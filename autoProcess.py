#!/usr/bin/env python


###############################
##variables
###############################
#AMI ID for front end servers
FEAMI = "ami-7c0a4614" #for front end change!
INSTTYPE = "m1.large" #for front end change!
KEYNAME = '15319demo' #for sshing change!
SUBNETID = "subnet-95b465be"

SECURITYGROUPNAME = "http_script"
SECURITYGROUPDESC = "http_via_script"


###########################################
##Code by Aaron Hsu ahsu1@andrew.cmu.edu
##########################################
import boto.ec2
import time
import urllib2
import math
CONN = boto.ec2.connect_to_region("us-east-1")

import boto.ec2.elb
from boto.ec2.elb import HealthCheck

import boto.ec2.autoscale
from boto.ec2.autoscale import LaunchConfiguration
from boto.ec2.autoscale import AutoScalingGroup
from boto.ec2.autoscale import ScalingPolicy
from boto.ec2.autoscale.tag import Tag

import boto.ec2.cloudwatch
from boto.ec2.cloudwatch import MetricAlarm
ELB = boto.ec2.elb.connect_to_region("us-east-1")
AUTOSCALE = boto.ec2.autoscale.connect_to_region("us-east-1")
CLOUDWATCH = boto.ec2.cloudwatch.connect_to_region('us-east-1')




###############################
##variables
###############################
#AMI ID for front end servers
FEAMI = "ami-7c0a4614" #for front end
INSTTYPE = "m1.large" #for front end
KEYNAME = '15319demo' #for sshing
SUBNETID = "subnet-95b465be"

SECURITYGROUPNAME = "http_script"
SECURITYGROUPDESC = "http_via_script"

###########################
####constants
###########################
REGION = 'us-east-1a'

INTOK = 16
INTTERMINATED = 48

LBPORT = [(80, 80, 'http')]
LBNAME = "lb-1"

HCINTERVAL = 30
HCUNHEALTHY = 4
HCHEALTHY = 3
HCTIMEOUT = 5

CONF_NAME = "launch_conf"

SG_NAME = "scale_group"
MIN_SIZE = 1
MAX_SIZE = 1
HCPERIOD = "120"
SG_CD = 120



#######################################
###gets the instance object by the id
######################################
def getInstance(get_id):
	reservations = CONN.get_all_reservations()
	for instance in reservations:
		if instance.instances[0].id == get_id:
			return instance.instances[0]
	print(get_id)
	raise Exception("ID not found")

######################################################
#####gets the two strings representing the 
#####system_status and instance_status by the id
#####################################################
def getStatuses(get_id):
	statuses = CONN.get_all_instance_status()
	for instance in statuses:
		if instance.id == get_id:
			return (str(instance.system_status.status), str(instance.instance_status.status))
	print(get_id)
	raise Exception("Status not found")

#############################
####security group
#############################
#delete existing security group
#then creates a new one
def createSecurityGroup(from_p,to_p):
	try:
		blah = CONN.delete_security_group(SECURITYGROUPNAME)
		print(blah)
		print(SECURITYGROUPNAME + " deleted")
	except:
		print(SECURITYGROUPNAME + " cant be deleted")
	#create security group
	time.sleep(2)
	security = CONN.create_security_group(
		SECURITYGROUPNAME,
		SECURITYGROUPDESC)
	time.sleep(2)
	#add rule
	authorized = security.authorize(
		ip_protocol="tcp",
		from_port=from_p,
		to_port=to_p,
		cidr_ip="0.0.0.0/0")
	print("Security group created and rules added")
	time.sleep(2)
	return

#############################
####get security group ID
#############################
def get_securityID(security_group_name):
	return str(CONN.get_all_security_groups(groupnames=[security_group_name])[0].id)

######################################################
#####create load balancer
######################################################
def createLoadBalancer():
	print "Creating loadbalancer.."
	hc = HealthCheck(
		interval=HCINTERVAL,
		healthy_threshold=HCHEALTHY,
        unhealthy_threshold=HCUNHEALTHY,
        target='HTTP:80/heartbeat',
        timeout=HCTIMEOUT)
	security_id = get_securityID(SECURITYGROUPNAME)
	lb = ELB.create_load_balancer(LBNAME, [REGION],LBPORT, security_groups=security_id)
	time.sleep(30)
	lb.configure_health_check(hc)
	time.sleep(10)
	try:
		lb.disable_zones(['us-east-1b'])
		lb.disable_zones(['us-east-1c'])
		lb.disable_zones(['us-east-1d'])
		lb.disable_zones(['us-east-1e'])
	except:
		print("Some zones not deletable")
	time.sleep(5)
	print "cross zone disabled= ", lb.disable_cross_zone_load_balancing()
	return
	##ELB can't tag


######################################################
#####creates scaling group
######################################################
def createScalingGroup(input_lc):
	print "creating scaling group"
	tag = Tag(key="15619project",value="phase1",propagate_at_launch=True,resource_id=SG_NAME)
	ag = AutoScalingGroup(name=SG_NAME, load_balancers=[LBNAME],
                          availability_zones=['us-east-1a'],
                          launch_config=input_lc, min_size=MIN_SIZE, max_size=MAX_SIZE,
                          connection=AUTOSCALE,health_check_type="ELB",default_cooldown=60,
                          health_check_period=HCPERIOD,tags=[tag],
                          )


	AUTOSCALE.create_auto_scaling_group(ag)
	time.sleep(10)
	return ag

######################################################
#####creates launch configuration
######################################################
def createLaunchConfiguration():
	print "creating launch configuration..."
	oldconfiglst = AUTOSCALE.get_all_launch_configurations()
	if len(oldconfiglst)!=0:
		oldconfig = oldconfiglst[0]
		try:
			AUTOSCALE.delete_launch_configuration(str(oldconfig.name))
		except:
			print "no previous launch configuration"
	time.sleep(5)
	
	security_id = get_securityID(SECURITYGROUPNAME)
	lc = LaunchConfiguration(name=CONF_NAME, image_id=FEAMI,
                             key_name=KEYNAME,
                             security_groups=[security_id],
                             instance_type=INSTTYPE,
                             instance_monitoring=False,
                             )
	AUTOSCALE.create_launch_configuration(lc)
	time.sleep(10)
	return lc

#################
##not used yet
#################
def terminateAllProcesses(ag,lc,lb):
	print "test ended, terminating.."
	ag.shutdown_instances()
	time.sleep(30)
	ag.delete()
	lc.delete()
	lb.delete()


def main():

	createSecurityGroup(0,65535)
	lb = createLoadBalancer()
	lc = createLaunchConfiguration()
	ag = createScalingGroup(lc)
	print "all created"
	
	#information on environment
	lb = ELB.get_all_load_balancers(load_balancer_names=[LBNAME])[0]
	group = AUTOSCALE.get_all_groups(names=[SG_NAME])[0]
	instance_ids = [i.instance_id for i in group.instances]
	reservations = CONN.get_all_instances(instance_ids)
	instances = [i for r in reservations for i in r.instances]
	print "\n---------------------------" 
	print "RESULTS:"
	print "loadbalancer.dns_name= ",lb.dns_name,"name",LBNAME
	print "autoscalegroup.name= ",SG_NAME, "autoscale", group
	for i in instances:
		print  "instance.id= ", i.id, ",dns_name= ",i.public_dns_name,",private_ip= ",i.private_ip_address
	print ""
	

main()
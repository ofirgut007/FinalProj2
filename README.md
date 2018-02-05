# FinalProj2


1. Setting up an AWS machine

   1.1. Choose ```t2.small``` as the type
  
   1.2. Set the tags with : name = JENKINS
  
   1.3. Create a key.pem and share it with your team members
  
   1.4. Launch the machine
  
2. Installations

   2.1. ssh to the machine with "ec2-user"
 
   2.2. Install Jenkins LTS on it
  
   2.3. Install git
  
   2.4. Install openjdk
  
   2.5. Install Docker
   
3. git setup
  
   3.1. run git config with your github username and email
  
   3.2. setup a github new repository called **FinalProject**
  
   3.3. Fork the Sprinboot from lidorlg github account
4. Jenkins setup
  
   4.1. install all default plugins
  
   4.2. add your team members under Manage jenkins -> manage users -> create users
  
   4.3. Install the following additional plugins:
  
        - ChuckNorris
		- Docker API
		- Docker Pipeline
		- Docker 
		- github
		- Global slack notifier
		- green balls
		- groovy
		- groovy postbuild
		- junit
		- maven info
		- pipeline maven integration
		- pipeline groovy
		- pipeline shared groovy libraries
		- purge job history
		- workspace cleanup
		
   4.1. Configure all of the plug-ins
    
	    - Docker will point to the local installation
		
		- Slack notifier parameters to our slack channel
		
		- Maven will be installed automatically
	
5. Docker setup
  
   5.1.
  
   5.1.
  
6. Job Setup

  
curl http://packages.stardog.com/stardog.gpg.pub | sudo apt-key add
echo "deb http://packages.stardog.com/deb/ stable main" | sudo tee -a /etc/apt/sources.list

sudo apt-get -y update
sudo apt-get install -y stardog

export STARDOG_HOME=/var/opt/stardog

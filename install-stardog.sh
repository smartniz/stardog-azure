curl http://packages.stardog.com/stardog.gpg.pub | sudo apt-key add
echo "deb http://packages.stardog.com/deb/ stable main" | sudo tee -a /etc/apt/sources.list

sudo apt-get -y update
sudo apt-get install -y stardog

export STARDOG_HOME=/var/opt/stardog
export STARDOG_SERVER_JAVA_ARGS="$STARDOG_SERVER_JAVA_ARGS -Dstardog.license.trial.auto.download.email=$1"

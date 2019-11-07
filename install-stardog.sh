curl http://packages.stardog.com/stardog.gpg.pub | sudo apt-key add
echo "deb http://packages.stardog.com/deb/ stable main" | sudo tee -a /etc/apt/sources.list

sudo apt-get -y update
sudo apt-get install -y stardog

echo "export STARDOG_SERVER_JAVA_ARGS='-Dstardog.license.trial.auto.download.email=$1'" | sudo tee -a /etc/stardog.env.sh

sudo systemctl start stardog
EMAIL=$1
ADMIN_USER=$2
ADMIN_PASS=$3

curl http://packages.stardog.com/stardog.gpg.pub | sudo apt-key add
echo "deb http://packages.stardog.com/deb/ stable main" | sudo tee -a /etc/apt/sources.list

sudo apt-get -y update
sudo apt-get install -y stardog

echo "export STARDOG_SERVER_JAVA_ARGS='-Dstardog.license.trial.auto.download.email=$EMAIL'" | sudo tee -a /etc/stardog.env.sh

sudo systemctl start stardog
sleep 20 # Gross

if [ "$ADMIN_USER" = "admin" ]; then
  if [ "$ADMIN_PASS" != "admin" ]; then
    /opt/stardog/bin/stardog-admin user passwd --new-password "$ADMIN_PASS"
  fi
else
  /opt/stardog/bin/stardog-admin user add --new-password "$ADMIN_PASS" --superuser "$ADMIN_USER"
  /opt/stardog/bin/stardog-admin user remove --username "$ADMIN_USER" --passwd "$ADMIN_PASS" admin
fi
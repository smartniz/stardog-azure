curl http://packages.stardog.com/stardog.gpg.pub | sudo apt-key add
echo "deb http://packages.stardog.com/deb/ stable main" | sudo tee -a /etc/apt/sources.list
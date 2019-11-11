# stardog-azure

# Install Stardog on an Ubuntu Virtual Machine

<a href="https://portal.azure.com/#create/Microsoft.Template/uri/https%3A%2F%2Fraw.githubusercontent.com%2Fstardog-union%2Fstardog-azure%2Fmaster%2Fazuredeploy.json" target="_blank">
    <img src="https://raw.githubusercontent.com/Azure/azure-quickstart-templates/master/1-CONTRIBUTION-GUIDE/images/deploytoazure.png"/>
</a>

## This template is a WIP. As it currently exists, it WILL NOT WORK. YOU HAVE BEEN WARNED.

This template deploys [Stardog](https://stardog.com) on an Ubuntu Virtual Machine. This template also deploys a Storage Account, Virtual Network, Public IP address and a Network Interface.

### Usage

Click the "Deploy" button above to deploy this template on your Azure account. You will need to set the following parameters:

| Name | Description |
|:--- |:--- |
| newStorageAccountName | The name to use for the storage account |
| vmSize | The size/type of VM to use |
| dnsNameForPublicIP | The unique DNS name for the VM's public IP address |
| adminUsername | The username you wish to assign to your VM's admin account |
| authenticationType | The type of authentication for the VM. SSH key is recommended |
| adminPasswordOrKey | The SSH public key or password for the VM. SSH key is recommended |
| email | An email address to use when downloading the Stardog trial license |
| stardogAdminUser | The username for the Stardog superuser (default: admin) |
| stardogAdminPassword | The password for the Stardog superuser (default: admin) |

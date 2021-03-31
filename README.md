# EdanProject
Android application for the edan project

# Configure GCP project
Create a Google Cloud Platform project, configure an Api for all services (at least for maps and analytics)
Then in firabse register this app and get google-services.json

# Add web services urls and key signings
In local.properties add key-values for urls and key signing
e.g. galdos_url = https://edanapp.indeci.gob.pe/

You can see the build.grale (app module) the required fields, some of them are the following

- galdos_url
- reniec_url
- signing.key.alias
- signing.key.password
- signing.store.file
- signing.store.password

const session = require('express-session');
const Keycloak = require('keycloak-connect');
require('dotenv').config();

const memoryStore = new session.MemoryStore();


const keycloakConfig = {
    realm: 'MicroProject',
    serverUrl: 'http://keycloak:8180/auth',
    'ssl-required': 'external',
    resource: 'gestionUser',
    //'public-client': false,
    'confidential-port': 0,
    //bearerOnly : true,
    publicClient: false,
    bearerOnly: true,
  
    realmPublicKey : '3chUvMA7cxOcKcLwEK7QrATbLfkVgMc2'
    
};




const keycloak = new Keycloak({ store: memoryStore ,  scope: 'offline_access'  }, keycloakConfig);

module.exports = { keycloak, memoryStore };
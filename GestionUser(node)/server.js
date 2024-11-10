const express = require('express');
const Eureka = require('eureka-js-client').Eureka;
const connectDB = require('./configurations/db');
const authRoutes = require('./Routes/AuthRoutes');
const { keycloak, memoryStore } = require('./keycloakConfig'); // Assurez-vous que le chemin est correct
const session = require('express-session');
const cors = require('cors');
const AuthController = require('./Controllers/AuthController');
require('dotenv').config();


const client = new Eureka({
    instance: {
        instanceId: `USER:${Math.random().toString(36).substring(2, 15)}`,
        app: 'USER',
        hostName: 'gestionUser',
        ipAddr: '0.0.0.0',
        port: { '$': 4000, '@enabled': true },
        vipAddress: 'USER',
        secure: false,
        statusPageUrl: 'http://gestionUser:4000/health',
        healthCheckUrl: 'http://gestionUser:4000/health',
        homePageUrl: 'http://gestionUser:4000',
        dataCenterInfo: {
            name: 'MyOwn',
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
        }
    },
    eureka: {
        host: 'discovery',
        port: 8761,
        servicePath: '/eureka/apps/'
    }
});

client.start((error) => {
    if (error) {
        console.error('Error starting the Eureka Client', error);
    } else {
        console.log('Eureka client started');
    }
});

const app = express();
const PORT = 4000;



connectDB();



// Configurez la session


// Middleware
app.use(express.json());
// app.use(cors({
//     origin: ['http://gestionUser:4000', 'http://localhost:4000'],
//     credentials: true
// }));
app.use(cors({
    origin: true,
    credentials: true
}));


app.use(session({
    secret: 'some secrety',
    resave: false,
    saveUninitialized: true,
    store: memoryStore,
    //cookie: { secure: false }
}));

app.use(keycloak.middleware());

// Protected routes
//app.use('/api/auth', authRoutes);

app.get('/health', (req, res) => {
    res.status(200).send('OK');
});

// Protected routes
app.use('/api/auth', authRoutes);

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});

// Export app and keycloak
// Export app and keycloak
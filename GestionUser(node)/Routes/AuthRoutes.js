const express = require('express');
const AuthController = require('../Controllers/AuthController');
const router = express.Router();
const { keycloak } = require('../keycloakConfig');
const authMiddleware = require('../Middleware/middleware'); // Import de l'instance de Keycloak

//console.log('Keycloak instanceee1:', keycloak);

//router.post('/register', AuthController.register);
//router.post('/register', keycloak.protect(), AuthController.register);
// Route with Keycloak protection
// Routes protégées par rôle
// Protected routes
router.post('/register', AuthController.register);

router.get('/test', authMiddleware, (req, res) => {
    res.send('Protected route accessed');
});

// Route de connexion
router.post('/login', AuthController.login);

// Autres routes protégées
//router.get('/', keycloak.protect('realm:Teacher'), AuthController.getAllUsers);
router.get('/getall',AuthController.findusers);
router.get('/by-speciality-role', AuthController.getUsersBySpecialityAndRole);
router.get('/getby/:id',  AuthController.getUserById);
router.get('/getbymail/:email',  AuthController.getUserByEmail);
router.get('/getbykeykclockid/:id',  AuthController.getUseBykeycklock);
router.get('/:id', AuthController.getUserById);
router.put('/:id', AuthController.updateUser);
router.delete('/:id', AuthController.deleteUser);


// Example protected route for getting user details


module.exports = router;
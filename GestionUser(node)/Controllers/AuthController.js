// controllers/UserController.js
const UserService = require('../Services/AuthService');
const axios = require('axios');
const amqp = require('amqplib');
const jwt = require('jsonwebtoken');
const User = require('../Models/User');




class UserController {


  
  /* async register(req, res) {
        try {
            
            const { default: KcAdminClient } = await import('@keycloak/keycloak-admin-client');
            
            const kcAdminClient = new KcAdminClient({
                baseUrl: 'http://keycloak:8180', // Ensure this is correct
                realmName: 'MicroProject'
            });
            const { username, email, password, firstName, lastName, role } = req.body;

            // Check if the user already exists in the database
            const existingUser = await User.findOne({ email });
            if (existingUser) {
                return res.status(400).json({ message: 'User already exists' });
            }

         
            console.log('KcAdminClient initialized');
            
            console.log('Attempting to authenticate...');
            await kcAdminClient.auth({
                clientId: 'admin-cli',
                grantType: 'client_credentials',
                clientSecret: 'HWqbVAqaY9zz0Rz2UnODQ3aIUq8VAbSU' // Ensure this is correct
            });
            const keycloakUser = await kcAdminClient.users.create({
                username,
                email,
                enabled: true,
                credentials: [{
                    type: 'password',
                    value: password,
                    temporary: false
                }],
                firstName,
                lastName
            });

            if (role) {
                const roles = await kcAdminClient.roles.find();
                const userRole = roles.find(r => r.name === role);
                if (userRole) {
                    await kcAdminClient.users.addRealmRoleMappings({
                        realm: 'MicroProject',
                        id: keycloakUser.id,
                        roles: [userRole]
                    });
                }
            }

            const user = new User({
                idkeycklock: keycloakUser.id,
                username,
                email,
                password,
                firstName,
                lastName,
                role
            });

            await user.save();

            res.status(201).json({
                message: 'User registered successfully',
                user: {
                    id: user._id,
                    username,
                    email,
                    role
                }
            });
        } catch (error) {
            console.error('Registration error:', error);
            res.status(500).json({ message: 'Error registering user', error: error.message });
        }
    }*/
    // async findusers(req, res) {
    //     try {
    //         console.log('Starting findusers function');
            
    //         const { default: KcAdminClient } = await import('@keycloak/keycloak-admin-client');
    //         console.log('KcAdminClient imported successfully');
                
    //         const kcAdminClient = new KcAdminClient({
    //             baseUrl: 'http://keycloak:8180', // Retirez le /auth
    //             realmName: 'MicroProject'
    //         });
    //         console.log('KcAdminClient initialized');
            
    //         console.log('Attempting to authenticate...');
    //         await kcAdminClient.auth({
    //             username: ' admin',
    //             password: 'admin',
    //             //grantType: 'password',
    //             clientId: 'admin-cli',
    //             grantType: 'client_credentials',
             
    //             clientSecret: 'ViGlv41pdSMz4TG6o8h5xDkkeFSqph3R' // Utilisez admin-cli pour l'authentification admin
               
    //         });
    //         console.log('Authentication successful');
    
    //         console.log('Fetching users...');
    //         const users = await kcAdminClient.users.find({
    //             realm: 'MicroProject'
    //         });
    //         console.log('Users found:', users);
            
    //         return res.status(200).json(users);
    
    //     } catch (error) {
    //         console.error('Error in findusers:', error);
    //         return res.status(500).json({ 
    //             message: 'Internal server error', 
    //             error: error.message,
    //             stack: error.stack 
    //         });
    //     }
    // }
    // Méthode pour le login
    // controllers/AuthController.js
    
    async register(req, res) {
        try {
            const { default: KcAdminClient } = await import('@keycloak/keycloak-admin-client');
            
            const kcAdminClient = new KcAdminClient({
                baseUrl: 'http://keycloak:8180', // Make sure this is correct
                realmName: 'MicroProject'
            });
    
            const { username, email, password, firstName, lastName, role } = req.body;
    
            // Check if the user already exists in the database
            const existingUser = await User.findOne({ email });
            if (existingUser) {
                return res.status(400).json({ message: 'User already exists' });
            }
    
            console.log('KcAdminClient initialized');
            
            console.log('Attempting to authenticate...');
            await kcAdminClient.auth({
                clientId: 'admin-cli',
                grantType: 'client_credentials',
                clientSecret: 'HWqbVAqaY9zz0Rz2UnODQ3aIUq8VAbSU' // Ensure this is correct
            });
    
            // Create the user in Keycloak
            const keycloakUser = await kcAdminClient.users.create({
                username,
                email,
                enabled: true,
                credentials: [{
                    type: 'password',
                    value: password,
                    temporary: false
                }],
                firstName,
                lastName
            });
    
            // Assign realm-level role if specified
            if (role) {
                const roles = await kcAdminClient.roles.find();
                const userRole = roles.find(r => r.name === role);
                if (userRole) {
                    await kcAdminClient.users.addRealmRoleMappings({
                        realm: 'MicroProject',
                        id: keycloakUser.id,
                        roles: [userRole]
                    });
                }
            }
    
            // Assign specific roles in `Forum` and `gestionUser` resources
            const clients = await kcAdminClient.clients.find();
            
            // Get the Forum client
            const forumClient = clients.find(client => client.clientId === 'Forum');
            if (forumClient) {
                const forumRoles = await kcAdminClient.clients.listRoles({ id: forumClient.id });
                const forumRole = forumRoles.find(r => r.name === role);
                if (forumRole) {
                    await kcAdminClient.users.addClientRoleMappings({
                        id: keycloakUser.id,
                        clientUniqueId: forumClient.id,
                        roles: [forumRole]
                    });
                }
            }
    
            // Get the gestionUser client
            const gestionUserClient = clients.find(client => client.clientId === 'gestionUser');
            if (gestionUserClient) {
                const gestionUserRoles = await kcAdminClient.clients.listRoles({ id: gestionUserClient.id });
                const gestionUserRole = gestionUserRoles.find(r => r.name === role);
                if (gestionUserRole) {
                    await kcAdminClient.users.addClientRoleMappings({
                        id: keycloakUser.id,
                        clientUniqueId: gestionUserClient.id,
                        roles: [gestionUserRole]
                    });
                }
            }
    
            // Save the user in MongoDB
            const user = new User({
                idkeycklock: keycloakUser.id,
                username,
                email,
                password,
                firstName,
                lastName,
                role
            });
    
            await user.save();
    
            res.status(201).json({
                message: 'User registered successfully',
                user: {
                    id: user._id,
                    username,
                    email,
                    role
                }
            });
        } catch (error) {
            console.error('Registration error:', error);
            res.status(500).json({ message: 'Error registering user', error: error.message });
        }
    }
    
    
    
    
    
    
    async  findusers(req, res) {
        try {
            console.log('Starting findusers function');
            
            const { default: KcAdminClient } = await import('@keycloak/keycloak-admin-client');
            console.log('KcAdminClient imported successfully');
                
            const kcAdminClient = new KcAdminClient({
                baseUrl: 'http://keycloak:8180', // Ensure this is correct
                realmName: 'MicroProject'
            });
            console.log('KcAdminClient initialized');
            
            console.log('Attempting to authenticate...');
            await kcAdminClient.auth({
                clientId: 'admin-cli',
                grantType: 'client_credentials',
                clientSecret: 'HWqbVAqaY9zz0Rz2UnODQ3aIUq8VAbSU' // Ensure this is correct
            });
            console.log('Authentication successful');
    
            console.log('Fetching users...');
            const users = await kcAdminClient.users.find({
                realm: 'MicroProject'
            });
            console.log('Users found:', users);
            
            return res.status(200).json(users);
    
        } catch (error) {
            console.error('Error in findusers:', error);
            return res.status(500).json({ 
                message: 'Internal server error', 
                error: error.message,
                stack: error.stack 
            });
        }
    }
   
   
    async login(req, res) {
        try {
            const { email, password } = req.body;
            const { user, token } = await UserService.loginUser(email, password);

            // Stocker le token dans l'en-tête de réponse
            res.setHeader('Authorization', `Bearer ${token}`);

            return res.status(200).json({ user }); // Inclure seulement l'utilisateur dans la réponse
        } catch (error) {
            return res.status(401).json({ message: 'Invalid email or password' });
        }
    }




 // Méthode pour obtenir tous les utilisateurs
 async getAllUsers(req, res) {
    try {
        const users = await UserService.getAllUsers(); // Correction ici: UserService
        return res.status(200).json(users);
    } catch (error) {
        return res.status(500).json({ message: "Error fetching users", error: error.message });
    }
}



    // Méthode pour obtenir tous les utilisateurs
    async getAllUsersFromkeykloak(req, res) {
        try {
            const users = await UserService.getAllUsers(); // Correction ici: UserService
            return res.status(200).json(users);
        } catch (error) {
            return res.status(500).json({ message: "Error fetching users", error: error.message });
        }
    }

    // Méthode pour obtenir un utilisateur par ID
    async getUserById(req, res) {
        try {
            const user = await UserService.getUserById(req.params.id); // Correction ici: UserService
            if (!user) {
                return res.status(404).json({ message: "User not found" });
            }
            return res.status(200).json(user);
        } catch (error) {
            return res.status(500).json({ message: "Error fetching user", error: error.message });
        }
    }
    async getUserByEmail(req, res) {
        try {
            const email = req.params.email; // Assurez-vous que l'email est passé comme paramètre dans l'URL
            console.log("Email received:", email);
            
            const user = await UserService.getUserByEmail(email);
            
            if (!user) {
                console.log("User not found");
                return res.status(404).json({ message: "User not found" });
            }
            
            return res.status(200).json(user);
        } catch (error) {
            console.error("Error fetching user:", error);
            return res.status(500).json({ message: "Error fetching user", error: error.message });
        }
    }
    
    async getUserBykeyklockid(req, res) {
        try {
            const user = await UserService.getUserBykeycklcokid(req.params.idkeycklock); // Correction ici: UserService
            if (!user) {
                return res.status(404).json({ message: "User not found" });
            }
            return res.status(200).json(user);
        } catch (error) {
            return res.status(500).json({ message: "Error fetching user", error: error.message });
        }
    }
    async getUseBykeycklock(req, res) {
        try {
            const user = await UserService.getUseByKey(req.params.id); // Correction ici: UserService
            if (!user) {
                return res.status(404).json({ message: "User not found" });
            }
            return res.status(200).json(user);
        } catch (error) {
            return res.status(500).json({ message: "Error fetching user", error: error.message });
        }

    }


    // async deleteUser(req, res) {
    //     try {
    //         // Supprimer l'utilisateur
    //         const user = await UserService.deleteUser(req.params.id);
    //         if (!user) {
    //             return res.status(404).json({ message: "User not found" });
    //         }

    //         // Connexion à RabbitMQ
    //         const connection = await amqp.connect('amqp://rabbitmq');
    //         const channel = await connection.createChannel();

    //         const userId = req.params.id;
    //         const message = JSON.stringify({ userId });

    //         // File d'attente pour supprimer les questions
    //         const deleteQuestionsQueue = 'delete-questions-queue';
    //         await channel.assertQueue(deleteQuestionsQueue, { durable: true });
    //         await channel.sendToQueue(deleteQuestionsQueue, Buffer.from(message), { persistent: true });

    //         console.log(`Message envoyé à ${deleteQuestionsQueue} pour supprimer les questions de l'utilisateur ${userId}`);

    //         // File d'attente pour supprimer les réponses
    //         const deleteAnswersQueue = 'delete-answers-queue';
    //         await channel.assertQueue(deleteAnswersQueue, { durable: true });
    //         await channel.sendToQueue(deleteAnswersQueue, Buffer.from(message), { persistent: true });

    //         console.log(`Message envoyé à ${deleteAnswersQueue} pour supprimer les réponses de l'utilisateur ${userId}`);

    //         // Répondre à l'API
    //         return res.status(200).json({
    //             message: "User deleted, questions and answers will be deleted asynchronously."
    //         });

    //     } catch (error) {
    //         console.error(error.message);
    //         return res.status(500).json({ message: "Error deleting user", error: error.message });
    //     }
    // }


    // Méthode pour supprimer un utilisateur
   
    async deleteUser(req, res) {
        try {
            const userId = req.params.id;
    
            // Find and delete user from your database
            const user = await User.findByIdAndDelete(userId);
            if (!user) {
                return res.status(404).json({ message: "User not found" });
            }
    
            // Initialize Keycloak Admin Client
            const { default: KcAdminClient } = await import('@keycloak/keycloak-admin-client');
            const kcAdminClient = new KcAdminClient({
                baseUrl: 'http://keycloak:8180',
                realmName: 'MicroProject'
            });
    
            // Authenticate Keycloak Admin Client
            await kcAdminClient.auth({
                clientId: 'admin-cli',
                grantType: 'client_credentials',
                clientSecret: 'HWqbVAqaY9zz0Rz2UnODQ3aIUq8VAbSU'
            });
    
            // Delete user from Keycloak
            await kcAdminClient.users.del({
                id: user.idkeycklock
            });
    
            // Connect to RabbitMQ
            const connection = await amqp.connect('amqp://rabbitmq');
            const channel = await connection.createChannel();
    
            const message = JSON.stringify({ userId });
    
            // Queue for deleting questions
            const deleteQuestionsQueue = 'delete-questions-queue';
            await channel.assertQueue(deleteQuestionsQueue, { durable: true });
            await channel.sendToQueue(deleteQuestionsQueue, Buffer.from(message), { persistent: true });
    
            console.log(`Message sent to ${deleteQuestionsQueue} for deleting user's questions: ${userId}`);
    
            // Queue for deleting answers
            const deleteAnswersQueue = 'delete-answers-queue';
            await channel.assertQueue(deleteAnswersQueue, { durable: true });
            await channel.sendToQueue(deleteAnswersQueue, Buffer.from(message), { persistent: true });
    
            console.log(`Message sent to ${deleteAnswersQueue} for deleting user's answers: ${userId}`);
    
            // Close the channel and connection
            await channel.close();
            await connection.close();
    
            // Respond to the API
            return res.status(200).json({
                message: "User deleted, questions and answers will be deleted asynchronously."
            });
    
        } catch (error) {
            console.error('Error deleting user:', error);
            return res.status(500).json({ message: "Error deleting user", error: error.message });
        }
    }
   
    async updateUser(req, res) {
        try {
            const user = await UserService.updateUser(req.params.id, req.body); // Correction ici: UserService
            if (!user) {
                return res.status(404).json({ message: "User not found" });
            }
            return res.status(200).json(user);
        } catch (error) {
            return res.status(500).json({ message: "Error updating user", error: error.message });
        }
    }
}



module.exports = new UserController();
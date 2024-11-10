# Dockerfile pour `gestionUser_A`
FROM node:20.10.0

# Définir le répertoire de travail
WORKDIR /usr/src/app

# Copier les fichiers de configuration
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier le reste du code de l'application
COPY . .

# Exposer le port utilisé par l'application
EXPOSE 4000

# Démarrer l'application
CMD ["npm", "start"]

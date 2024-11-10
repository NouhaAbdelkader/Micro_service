# Utiliser l'image Node.js version 18 comme base
FROM node:18

# Définir le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copier les fichiers de configuration de l'application (package.json et package-lock.json)
COPY package*.json ./

# Installer les dépendances de l'application
RUN npm install

# Copier tous les fichiers de l'application dans le conteneur
COPY . .

# Exposer le port 4000
EXPOSE 4000

# Définir la commande pour démarrer l'application Node.js
CMD ["node", "server.js"]

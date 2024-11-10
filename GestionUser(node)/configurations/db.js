const mongoose = require('mongoose');

const connectDB = async() => {
    try {
        const conn = await mongoose.connect('mongodb://najibagragba:najiba123@mongo:27017/UserDataBase?authSource=admin');
        //authSource=admin

        console.log(`MongoDB connected: ${conn.connection.host}`);
    } catch (error) {
        console.error(`Error: ${error.message}`);
        process.exit(1);
    }
};

module.exports = connectDB;
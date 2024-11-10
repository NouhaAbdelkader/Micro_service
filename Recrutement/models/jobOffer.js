const db = require("../config/db");

// Job Offer CRUD functions
const getAllJobOffers = () => {
  return new Promise((resolve, reject) => {
    db.query("SELECT * FROM job_offers", (err, results) => {
      if (err) reject(err);
      resolve(results);
    });
  });
};

const createJobOffer = (title, description, location, salary) => {
  return new Promise((resolve, reject) => {
    db.query(
      "INSERT INTO job_offers (title, description, location, salary) VALUES (?, ?, ?, ?)",
      [title, description, location, salary],
      (err, results) => {
        if (err) reject(err);
        resolve(results);
      }
    );
  });
};

const getJobOfferById = (id) => {
  return new Promise((resolve, reject) => {
    db.query("SELECT * FROM job_offers WHERE id = ?", [id], (err, results) => {
      if (err) reject(err);
      resolve(results[0]);
    });
  });
};

const updateJobOffer = (id, title, description, location, salary) => {
  return new Promise((resolve, reject) => {
    db.query(
      "UPDATE job_offers SET title = ?, description = ?, location = ?, salary = ? WHERE id = ?",
      [title, description, location, salary, id],
      (err, results) => {
        if (err) reject(err);
        resolve(results);
      }
    );
  });
};

const deleteJobOffer = (id) => {
  return new Promise((resolve, reject) => {
    db.query("DELETE FROM job_offers WHERE id = ?", [id], (err, results) => {
      if (err) reject(err);
      resolve(results);
    });
  });
};

module.exports = {
  getAllJobOffers,
  createJobOffer,
  getJobOfferById,
  updateJobOffer,
  deleteJobOffer,
};

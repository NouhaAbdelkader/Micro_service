const db = require("../config/db");

// Applicant CRUD functions
const getAllApplicants = () => {
  return new Promise((resolve, reject) => {
    db.query("SELECT * FROM applicants", (err, results) => {
      if (err) reject(err);
      resolve(results);
    });
  });
};

const createApplicant = (name, email, phone, resume, job_offer_id) => {
  return new Promise((resolve, reject) => {
    db.query(
      "INSERT INTO applicants (name, email, phone, resume, job_offer_id) VALUES (?, ?, ?, ?, ?)",
      [name, email, phone, resume, job_offer_id],
      (err, results) => {
        if (err) reject(err);
        resolve(results);
      }
    );
  });
};

const getApplicantById = (id) => {
  return new Promise((resolve, reject) => {
    db.query("SELECT * FROM applicants WHERE id = ?", [id], (err, results) => {
      if (err) reject(err);
      resolve(results[0]);
    });
  });
};

const updateApplicant = (id, name, email, phone, resume, job_offer_id) => {
  return new Promise((resolve, reject) => {
    db.query(
      "UPDATE applicants SET name = ?, email = ?, phone = ?, resume = ?, job_offer_id = ? WHERE id = ?",
      [name, email, phone, resume, job_offer_id, id],
      (err, results) => {
        if (err) reject(err);
        resolve(results);
      }
    );
  });
};

const deleteApplicant = (id) => {
  return new Promise((resolve, reject) => {
    db.query("DELETE FROM applicants WHERE id = ?", [id], (err, results) => {
      if (err) reject(err);
      resolve(results);
    });
  });
};

module.exports = {
  getAllApplicants,
  createApplicant,
  getApplicantById,
  updateApplicant,
  deleteApplicant,
};

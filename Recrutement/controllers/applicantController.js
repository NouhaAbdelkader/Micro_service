const Applicant = require("../models/applicant");

const getAllApplicants = async (req, res) => {
  try {
    const applicants = await Applicant.getAllApplicants();
    res.json(applicants);
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const createApplicant = async (req, res) => {
  const { name, email, phone, resume, job_offer_id } = req.body;
  try {
    const result = await Applicant.createApplicant(
      name,
      email,
      phone,
      resume,
      job_offer_id
    );
    res.status(201).json({ id: result.insertId });
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const getApplicantById = async (req, res) => {
  const { id } = req.params;
  try {
    const applicant = await Applicant.getApplicantById(id);
    res.json(applicant);
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const updateApplicant = async (req, res) => {
  const { id } = req.params;
  const { name, email, phone, resume, job_offer_id } = req.body;
  try {
    await Applicant.updateApplicant(
      id,
      name,
      email,
      phone,
      resume,
      job_offer_id
    );
    res.send("Applicant updated successfully");
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const deleteApplicant = async (req, res) => {
  const { id } = req.params;
  try {
    await Applicant.deleteApplicant(id);
    res.send("Applicant deleted successfully");
  } catch (err) {
    res.status(500).send(err.message);
  }
};

module.exports = {
  getAllApplicants,
  createApplicant,
  getApplicantById,
  updateApplicant,
  deleteApplicant,
};

const JobOffer = require("../models/jobOffer");

const getAllJobOffers = async (req, res) => {
  try {
    const jobOffers = await JobOffer.getAllJobOffers();
    res.json(jobOffers);
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const createJobOffer = async (req, res) => {
  const { title, description, location, salary } = req.body;
  try {
    const result = await JobOffer.createJobOffer(
      title,
      description,
      location,
      salary
    );
    res.status(201).json({ id: result.insertId });
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const getJobOfferById = async (req, res) => {
  const { id } = req.params;
  try {
    const jobOffer = await JobOffer.getJobOfferById(id);
    res.json(jobOffer);
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const updateJobOffer = async (req, res) => {
  const { id } = req.params;
  const { title, description, location, salary } = req.body;
  try {
    await JobOffer.updateJobOffer(id, title, description, location, salary);
    res.send("Job Offer updated successfully");
  } catch (err) {
    res.status(500).send(err.message);
  }
};

const deleteJobOffer = async (req, res) => {
  const { id } = req.params;
  try {
    await JobOffer.deleteJobOffer(id);
    res.send("Job Offer deleted successfully");
  } catch (err) {
    res.status(500).send(err.message);
  }
};

module.exports = {
  getAllJobOffers,
  createJobOffer,
  getJobOfferById,
  updateJobOffer,
  deleteJobOffer,
};

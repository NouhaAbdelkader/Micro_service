const express = require("express");
const router = express.Router();
const jobOfferController = require("../controllers/jobOfferController");

router.get("/", jobOfferController.getAllJobOffers);
router.post("/", jobOfferController.createJobOffer);
router.get("/:id", jobOfferController.getJobOfferById);
router.put("/:id", jobOfferController.updateJobOffer);
router.delete("/:id", jobOfferController.deleteJobOffer);

module.exports = router;

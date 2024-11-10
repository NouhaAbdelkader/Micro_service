const express = require("express");
const bodyParser = require("body-parser");
const jobOfferRoutes = require("./routes/jobOfferRoutes");
const applicantRoutes = require("./routes/applicantRoutes");

const app = express();

// Middleware
app.use(bodyParser.json());

// Routes
app.use("/recrutement/job-offers", jobOfferRoutes);
app.use("/recrutement/applicants", applicantRoutes);

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(8087, () => {
  console.log("Recruitment module is running on http://localhost:8087");
});

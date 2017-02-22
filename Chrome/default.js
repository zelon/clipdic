
var skipButton = document.getElementById("skip_button");
skipButton.addEventListener("click", function(event) {
  location.href=getBaseUrl();
});

var option_button = document.getElementById("option_button");
option_button.addEventListener("click", function(event) {
  location.href="option.html";
});

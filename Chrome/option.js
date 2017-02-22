function initValue() {
  $("baseurl").value = getBaseUrl();
  if ( isPopupMode() ) {
    $("popup").checked = true;
  } else {
    $("tab").checked = true;
  }
}

initValue();

var reset_button = document.getElementById("reset_button");
reset_button.addEventListener("click", function(event) {
  resetDefaultValues();
  initValue();
});

var save_button = document.getElementById("save_button");
save_button.addEventListener("click", function(event) {
  setBaseUrl($("baseurl").value);
  setPopupMode($("popup").checked);
  alert("Saved");
});

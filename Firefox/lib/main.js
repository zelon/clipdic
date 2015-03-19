var buttons = require('sdk/ui/button/action');
var tabs = require("sdk/tabs");
var windows = require("sdk/windows").browserWindows;
var clipboard = require("sdk/clipboard");
var { setInterval, clearInterval } = require("sdk/timers");

var clipdic_window = null;
var clipdic_tab = null;

var timer_id = null;
var last_clipboard = "";

var button = buttons.ActionButton({
  id: "clipdic-link",
  label: "Clipdic",
  icon: {
    "16": "./icon16.png",
    "48": "./icon48.png",
  },
  onClick: handleClick
});

function startTimer() {
  var timer_interval = 500; //milliseconds
  timer_id = setInterval(onTimer, timer_interval);
}

function showDictionary(word) {
  if (clipdic_tab) {
    var new_url = "http://dic.daum.net/search.do?q=" + word;
    clipdic_tab.url = new_url;
  }
}

function onTimer() {
  var data = clipboard.get("text");

  console.log("ontimer:" + data);
  if (data != null) {
    if (last_clipboard != data) {
      last_clipboard = data;
      showDictionary(data);
    }
  }
}

function stopTimer() {
  if (timer_id != null) {
    clearInterval(timer_id);
    timer_id = null;
  }
}

function OnExit() {
  console.log("OnExit");
  stopTimer();
  clipdic_window = null;
  clipdic_tab = null;
}

function handleClick(state) {
  if (clipdic_tab) {
    clipdic_tab.activate();
    return;
  }
  windows.open({
    url: "http://dic.daum.net/",
    onOpen: function(window) {
      clipdic_window = window;
      clipdic_tab = window.tabs.activeTab;
      clipdic_tab.on('close', OnExit);
      startTimer();
    },
    onClose: OnExit
  });
}


function onOpen(tab) {
  console.log(tab.url + " is open");
  tab.on("pageshow", logShow);
  tab.on("activate", logActivate);
  tab.on("deactivate", logDeactivate);
  tab.on("close", logClose);
}

function logShow(tab) {
  console.log(tab.url + " is loaded");
}

function logActivate(tab) {
  console.log(tab.url + " is activated");
}

function logDeactivate(tab) {
  console.log(tab.url + " is deactivated");
}

function logClose(tab) {
  console.log(tab.url + " is closed");
}

tabs.on('open', onOpen);

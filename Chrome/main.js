
function getClipboard()
{
	var t = document.getElementById("inputbox");
	
	t.value = "";
	
	t.focus();
	document.execCommand('Paste');
	
	return t.value;
}

function copyToClipboard(aText)
{
	var t = document.getElementById("inputbox");
	
	t.value = aText;
	
	t.focus();
	document.execCommand('SelectAll');
	document.execCommand('Copy');
}

var lastTab = null;
var lastWindowId = null;
function onTabOpened(newTab)
{
	// To prevent many tabs opened, Remember ths last tab
	lastTab = newTab;
}

function onNewWindowOpened(newWindow)
{
	lastWindowId = newWindow.id;
	onTabOpened(newWindow.tabs[0]);
}

function openUrl(newUrl)
{
	if ( lastTab != null )
	{
		chrome.tabs.update(lastTab.id, { "url" : newUrl }, onTabOpened);
	}
	else
	{
		if ( isPopupMode() )
		{
			chrome.windows.create({"url" : newUrl, "type" : "popup", "width" : 500, "height" : 600 }, onNewWindowOpened);
		}
		else
		{
			chrome.tabs.create({ "url" : newUrl }, onTabOpened);
		}
	}
}

function openNewPage(word)
{
	var newUrl = getBaseUrl() + word;
	
	openUrl(newUrl);
}

function isValidWord(word)
{
	if ( word.startsWith("http://") ) return false;
	if ( word.startsWith("https://") ) return false;
	if ( word.startsWith("//") ) return false;
	if ( word == "" ) return false;
  if ( word.length > 31 ) return false;
	
	return true;
}

var lastValue = "";
function onTimer()
{
	var thisValue = getClipboard().trim();

	// if clipboard value is changed,
	if ( lastValue != thisValue )
	{
		lastValue = thisValue;

		if ( isValidWord(thisValue) )
		{
			openNewPage(thisValue);
		}
	}
}

function onClickedExtensionIcon()
{
	if ( lastTab == null )
	{
		appOn();
	}
	else
	{
		if ( isPopupMode() == false )
		{
			chrome.tabs.update(lastTab.id, { "selected" : true });
		}
		else
		{
			chrome.windows.update(lastWindowId, { "focused" : true, "state" : "normal"});
		}
	}
}

var timerID = 0;
function appOn()
{
	lastTab = null;
	timerID = window.setInterval(onTimer, 100);
	
	openUrl("default.html");
	chrome.browserAction.setIcon({path: 'icon16.png'});
}

function appOff()
{
	lastTab = null;
	lastWindowId = null;
	
	window.clearInterval(timerID);
	
	chrome.browserAction.setIcon({path: 'icon16_bw.png'});
}

function onRemovedTab(tabId, removeInfo)
{
	if ( lastTab != null )
	{
		if ( lastTab.id == tabId )
		{
			appOff();
		}
	}
}


chrome.tabs.onRemoved.addListener(onRemovedTab);
chrome.browserAction.onClicked.addListener(onClickedExtensionIcon);

function onSelection(info, tab)
{
	if ( info )
	{
		if ( info.selectionText )
		{
			copyToClipboard(info.selectionText);
			
			onClickedExtensionIcon();
		}
	}
}

chrome.contextMenus.create({ "title" : "Copy and search with ClipDic" , "contexts" : ["selection", "link"], "onclick" : onSelection }, null);


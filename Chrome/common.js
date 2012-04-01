
var baseUrlName = "baseurl";
var popupModeName = "popupmode";

String.prototype.startsWith = function (str) {
        return !this.indexOf(str);
	};

function $(id)
{
	return document.getElementById(id);
}

function setBaseUrl(newUrl)
{
	localStorage["baseurl"] = newUrl;
}

function getBaseUrl()
{
	var baseUrl = localStorage[baseUrlName];
	
	if ( baseUrl == null )
	{
		baseUrl = "http://dic.daum.net/search.do?q=";
	}
	
	return baseUrl;
}

function setPopupMode(bOn)
{
	if ( bOn == true )
	{
		localStorage[popupModeName] = "true";
	}
	else
	{
		localStorage[popupModeName] = "false";
	}
}

function isPopupMode()
{
	var popupMode = localStorage[popupModeName];
	
	if ( popupMode == null )
	{
		popupMode = "true";
	}
	
	return (popupMode == "true");
}

function resetDefaultValues()
{
	localStorage.removeItem(baseUrlName);
	localStorage.removeItem(popupModeName);
}

var baseUrlName = "baseurl";

function $(id)
{
	return document.getElementById(id);
}

function getBaseUrl()
{
	var baseUrl = localStorage[baseUrlName];
	
	if ( baseUrl == null )
	{
		baseUrl = "http://small.dic.daum.net/search.do?q=";
	}
	
	return baseUrl;
}

function resetDefaultValues()
{
	localStorage.removeItem(baseUrlName);
}
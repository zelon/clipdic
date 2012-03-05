
var baseUrlName = "baseurl";

String.prototype.startsWith = function (str) {
        return !this.indexOf(str);
	};

String.prototype.trim = function() {
	return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
	};
	
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
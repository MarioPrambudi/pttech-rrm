function updateCityList(state, cityUrl) {
    dojo.xhrGet(
    {
        url: cityUrl+state,
        handleAs: "json",
        load: function(request, ioArgs) {
            var cities = new Array();
            var option;
            // poll the request and create a list of new options
            // in the form of a value label pair.
            for (i in request) {
                var o =  request[i];
                option = {value: String(o.id), label: o.cityName};
                cities.push(option);
            }
            var u = dijit.byId('_city_id');
            var oids = new Array();
            for (i in u.options) {
                o = u.options[i];
                oids.push(o);
            }
            u.removeOption(oids);
            u.addOption(cities);
            if(document.getElementById('cityId')) {
                u.attr('value', document.getElementById('cityId').value);
            }
        }
    });
}

//To call Java method and verify the registration number
//result -1 = unique, 0 = duplicated, acquirerId = duplicated and deleted
function isDuplicateRegNo(regNo, acquirerUrl, validateUrl) {
    dojo.xhrGet(
    {
        url: validateUrl+regNo.value,
        handleAs: "json",
        load: function(result) {
        	
        	if(!(result=="-1"))
        	{
        		if(result=="0")
        		{        			
        			alert('The registration number is duplicated. Please insert a valid registration number.');
        			regNo.focus();
        		}	
        		else 
        		{
        			var reload;
        			var redirectUrl;
        			reload = confirm("The registration number is duplicated and deleted. Click on OK to enable it or Cancel to create a new acquirer.");
        			if(reload)
        			{	//enable the deleted acquirer
        				redirectUrl = acquirerUrl+result+"?form";
        				reloadAcquirerUpdate(redirectUrl);
        			}
        			else
        			{	//create new acquirer
        				redirectUrl = acquirerUrl+"?form";
        				reloadAcquirerCreate(redirectUrl);
        			}
        		}
        	}
        }

    });
}

//redirect to acquirer create form 
function reloadAcquirerCreate(redirectUrl) {
	window.location = redirectUrl;
}

//redirect to acquirer update form
function reloadAcquirerUpdate(redirectUrl) {
	window.location = redirectUrl;
}

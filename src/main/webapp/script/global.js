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

function isDuplicateRegNo(regNo, acquirerUrl) {
    dojo.xhrGet(
    {
        url: acquirerUrl+regNo.value,
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
        			reload = confirm("The registration number is duplicated and deleted. Do you want to enable it?");
        			if(reload)
        			{
        				reloadAcquirerUpdate(result);
        			}
        			else
        			{
        				reloadAcquirerCreate();
        			}
        		}
        	}
        }

    });
}

function reloadAcquirerCreate() {
	window.location = "/pttech-rrm/acquirers?form";
}

function reloadAcquirerUpdate(acquirerId) {
	window.location = "/pttech-rrm/acquirers/" + acquirerId + "?form";
}



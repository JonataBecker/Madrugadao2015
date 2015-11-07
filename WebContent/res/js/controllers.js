var hanasolo = angular.module('hanasolo', ['ui.bootstrap']);

hanasolo.controller('hanasoloController', function($scope, $filter, $http, $uibModal){
	
	var map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 4,
	    center: {lat: -29.6905592, lng: -51.1891802},
	    styles:[
	            {
	                "featureType": "road",
	                "elementType": "geometry",
	                "stylers": [
	                    {
	                        "visibility": "off"
	                    }
	                ]
	            },
	            {
	                "featureType": "poi",
	                "elementType": "geometry",
	                "stylers": [
	                    {
	                        "visibility": "off"
	                    }
	                ]
	            },
	            {
	                "featureType": "landscape",
	                "elementType": "geometry",
	                "stylers": [
	                    {
	                        "color": "#fffffa"
	                    }
	                ]
	            },
	            {
	                "featureType": "water",
	                "stylers": [
	                    {
	                    	 "color": "#94A2Af"
	                    }
	                ]
	            },
	            {
	                "featureType": "road",
	                "elementType": "labels",
	                "stylers": [
	                    {
	                        "visibility": "off"
	                    }
	                ]
	            },
	            {
	                "featureType": "transit",
	                "stylers": [
	                    {
	                        "visibility": "off"
	                    }
	                ]
	            },
	            {
	                "featureType": "administrative",
	                "elementType": "geometry",
	                "stylers": [
	                    {
	                        "lightness": 40
	                    }
	                ]
	            }
	        ]


	  });
	
	$scope.doencas = undefined;
	$scope.dadosPorMunicipio = [];
	$scope.dadosPorProcedimento = [];
	$scope.dadosPorHospital = [];
	$scope.doencaSelecionada = undefined;
	
	$http.get('https://equipe9bbc2bd985.us1.hana.ondemand.com/Madrugadao2015/api/G9_DADOS_DOENCAS?order.campo=MORTALIDADE&order.tipo=DESC').then(function(response) {
		$scope.doencas = response.data;
	});
	
	$scope.selecionaDoenca = function(doenca) {
		$scope.dadosPorMunicipio = undefined;
		$scope.dadosPorProcedimento = undefined;
		$scope.dadosPorHospital = undefined;
		$scope.doencaSelecionada = doenca;
		$scope.selecionaMucipio();
		
		$http.get('https://equipe9bbc2bd985.us1.hana.ondemand.com/Madrugadao2015/api/G9_DADOS_DOENCAS_MUNICIPIOS?filtro.campo=CO_CID&filtro.condicao=IGUAL&filtro.valor='+doenca.CO_CID).then(function(response) {
			
			$scope.dadosPorMunicipio = response.data;
			
			if ($scope.dadosPorMunicipio.length > 0) {
				map.setCenter(new google.maps.LatLng($scope.dadosPorMunicipio[0].NU_LATITUD, $scope.dadosPorMunicipio[0].NU_LONGIT));
				map.setZoom(6);
			}
			
			for(var i = 0; i < $scope.dadosPorMunicipio.length; i++) {
				$scope.addMarker(map, $scope.dadosPorMunicipio[i]);
			}
			
		});
		
		$http.get('https://equipe9bbc2bd985.us1.hana.ondemand.com/Madrugadao2015/api/G9_DADOS_DOENCAS_PROCEDIMENTOS?filtro.campo=CO_CID&filtro.condicao=IGUAL&filtro.valor='+doenca.CO_CID+'&order.campo=MORTALIDADE&order.tipo=ASC').then(function(response) {
			$scope.dadosPorProcedimento = response.data;
		});
	
	}
	
	$scope.selecionaMucipio = function(municipio) {
		$scope.dadosPorHospital = undefined;
		var filtro = 'filtro.campo=CO_CID&filtro.condicao=IGUAL&filtro.valor='+$scope.doencaSelecionada.CO_CID; 
		if (municipio != undefined) {
			filtro += '&filtro.campo=CO_MUNICIP&filtro.condicao=IGUAL&filtro.valor='+municipio.CO_MUNICIP;
		}
		
		$http.get('https://equipe9bbc2bd985.us1.hana.ondemand.com/Madrugadao2015/api/G9_DADOS_DOENCAS_MUNICIPIOS_HOSPITAIS?' + filtro + '&order.campo=MORTALIDADE&order.tipo=ASC').then(function(response) {
			$scope.dadosPorHospital = response.data;
		});
	}
	
	$scope.addMarker = function(map, municipio) {
		var percentual = $filter('number')(municipio.MORTALIDADE, 2) + "%";
		
		  // Add the marker at the clicked location, and add the next-available label
		  // from the array of alphabetical characters.
		var marker = new MarkerWithLabel({
			position: {lat: municipio.NU_LATITUD, lng: municipio.NU_LONGIT},
		    title: percentual,
		    labelContent: percentual,
	        labelClass: "maps_labels",
	        labelAnchor: new google.maps.Point(22, 5),
	        labelStyle: {opacity: 1},
	        icon: 'marker.png',
		    map: map
		});
		  
		  
		  	marker.addListener('click', function() {
			    $scope.selecionaMucipio(municipio);
		  	});

		}
	
	$scope.abreHistorico = function(CO_CNES) {
		var modalInstance = $uibModal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'historico.html',
		      controller: 'HistoricoController',
		      resolve: {
		    	  CO_CNES: function () {
		    		  return CO_CNES;
		    	  },
		    	  CO_CID: function() {
		    		  return $scope.doencaSelecionada.CO_CID;
		    	  }
		      }
		    });		
	};
	
});


hanasolo.controller('HistoricoController', function($scope, $http, CO_CNES, CO_CID){
	
	
	$http.get('https://equipe9bbc2bd985.us1.hana.ondemand.com/Madrugadao2015/api/G9_DADOS_HOSPITAL_DATA?order.campo=DT_INTER&order.tipo=ASC&filtro.campo=CO_CNES&filtro.condicao=IGUAL&filtro.valor=' + CO_CNES + '&filtro.campo=CO_CID&filtro.condicao=IGUAL&filtro.valor=' + CO_CID).then(function(response) {
		
		
		var arr = [];
		for (var i in response.data) {
			arr.push({x:new Date(response.data[i].DT_INTER).getTime(), y:response.data[i].MORTALIDADE})
		}
		
		
		
		// Create the chart
        $('#container').highcharts('StockChart', {

            title : {
                text : 'Histórico'
            },

            series : [{
                name : 'Mortalidade',
                data : arr,
                tooltip: {
                    valueDecimals: 2
                }
            }]
        });		
		
		
	});
	
	
	
});

function onGoogleReady() {
};

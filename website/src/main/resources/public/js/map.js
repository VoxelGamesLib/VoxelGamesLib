$(function () {

    var marker = [], infowindow = [], map, image = 'img/marker.png';

    function addMarker(location, name, contentstr) {
        marker[name] = new google.maps.Marker({
            position: location,
            map: map,
            icon: image
        });
        marker[name].setMap(map);

        infowindow[name] = new google.maps.InfoWindow({
            content: contentstr
        });

        google.maps.event.addListener(marker[name], 'click', function () {
            infowindow[name].open(map, marker[name]);
        });
    }

    function initialize() {

        var lat = $('#map-canvas').attr("data-lat");
        var lng = $('#map-canvas').attr("data-lng");

        var myLatlng = new google.maps.LatLng(lat, lng);

        var setZoom = parseInt($('#map-canvas').attr("data-zoom"));

        var styles = [{
            "featureType": "landscape",
            "stylers": [{"saturation": -100}, {"lightness": 65}, {"visibility": "on"}]
        }, {
            "featureType": "poi",
            "stylers": [{"saturation": -100}, {"lightness": 51}, {"visibility": "simplified"}]
        }, {
            "featureType": "road.highway",
            "stylers": [{"saturation": -100}, {"visibility": "simplified"}]
        }, {
            "featureType": "road.arterial",
            "stylers": [{"saturation": -100}, {"lightness": 30}, {"visibility": "on"}]
        }, {
            "featureType": "road.local",
            "stylers": [{"saturation": -100}, {"lightness": 40}, {"visibility": "on"}]
        }, {
            "featureType": "transit",
            "stylers": [{"saturation": -100}, {"visibility": "simplified"}]
        }, {
            "featureType": "administrative.province",
            "stylers": [{"visibility": "off"}]
        }, {
            "featureType": "water",
            "elementType": "labels",
            "stylers": [{"visibility": "on"}, {"lightness": -25}, {"saturation": -100}]
        }, {
            "featureType": "water",
            "elementType": "geometry",
            "stylers": [{"hue": "#ffff00"}, {"lightness": -25}, {"saturation": -97}]
        }];
        var styledMap = new google.maps.StyledMapType(styles, {name: "Styled Map"});

        var mapOptions = {
            scrollwheel: false,
            zoom: setZoom,

            panControl: false,
            panControlOptions: {
                position: google.maps.ControlPosition.LEFT_BOTTOM
            },
            zoomControl: true,
            zoomControlOptions: {
                style: google.maps.ZoomControlStyle.LARGE,
                position: google.maps.ControlPosition.LEFT_BOTTOM
            },
            streetViewControl: true,
            streetViewControlOptions: {
                position: google.maps.ControlPosition.LEFT_BOTTOM
            },

            center: myLatlng,
            mapTypeControlOptions: {
                mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'map_style']
            }

        };
        map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

        map.mapTypes.set('map_style', styledMap);
        map.setMapTypeId('map_style');


        $('.addresses-block a').each(function () {
            var mark_lat = $(this).attr('data-lat');
            var mark_lng = $(this).attr('data-lng');
            var this_index = $('.addresses-block a').index(this);
            var mark_name = 'template_marker_' + this_index;
            var mark_locat = new google.maps.LatLng(mark_lat, mark_lng);
            var mark_str = $(this).attr('data-string');
            addMarker(mark_locat, mark_name, mark_str);
        });

    }

    $(window).load(function () {
        setTimeout(function () {
            initialize();
        }, 500);
    });

});
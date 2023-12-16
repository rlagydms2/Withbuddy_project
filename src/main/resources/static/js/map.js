import { centerGu } from '../JSON/centerGu.js'; // ddd파일에서 export한 데이터를 가져옴
import { focusGu } from '../JSON/focusGu.js';

selectJson(setAreas);
let areas = [];
var path = [];
var hole = [];
var polygon1;
var ground = [];
var backup_polygon;
var listCnt;
var polygonData = [];
var hospital = [];

$(document).ready(function () {
    const list=[];
    CenterGu.forEach(e => {
        var title=e.title;
        list.push(title);
    });

    $.ajax({
        url: "/api/home",
        type: "POST",
        success: function (data) {
            console.log(data);
        },
    })
});

function setAreas(areas, data) {

    ground = [ // bgpolygon 사이드 좌표 설정
        new kakao.maps.LatLng(37.7772, 126.4401),
        new kakao.maps.LatLng(37.255, 126.4518),
        new kakao.maps.LatLng(37.289, 127.5161),
        new kakao.maps.LatLng(37.8002, 127.4228),
    ];


    $.each(data, function (index, val) {
        let obj = new Object();
        let coordinates = val.geometry.coordinates;
        let name = val.properties.SIG_KOR_NM;


        obj.name = name;
        path = [];

        $.each(coordinates[0], function (i, coordinates) {      //console.log(coordicates)를 확인해보면 [0]번재에 배열이 주로 저장이 됨. 그래서 [0]번째 배열에서 꺼내줌.
            path.push(new kakao.maps.LatLng(coordinates[1], coordinates[0]));   // new kakao.maps.LatLng가 없으면 인식을 못해서 path 배열에 추가.
            hole.push(new kakao.maps.LatLng(37.5676, 126.9778)); // bgpolygon hole 좌표 설정
        });

        obj.path = path;
        areas.push(obj);


    });

    bgpolygon(ground, hole)

    for (let i = 0, len = areas.length; i < len; i++) {
        displayArea(areas[i]);
    }

}

function selectJson(setAreas) {

    $.getJSON('../static/JSON/geojson', function (geojson) {
        var data = geojson.features;
        setAreas(areas, data);
    })
}

//////////////////////////////////////////////////////////////지도 형성//////////////////////////////////////////////////

let mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.5639, 126.9738), // 지도의 중심좌표
        level: 8,// 지도의 확대 레벨
        disableDoubleClickZoom: true
    };

let map = new kakao.maps.Map(mapContainer, mapOption),
    customOverlay = new kakao.maps.CustomOverlay({}),
    infowindow = new kakao.maps.InfoWindow({ removable: true });

map.setMaxLevel(8); //level 제한 메소드

// 버튼 클릭에 따라 지도 확대, 축소 기능을 막거나 풀고 싶은 경우에는 map.setZoomable 함수를 사용합니다
setZoomable(); // zoom제한 메소드

function setZoomable(zoomable) {
    // 마우스 휠로 지도 확대,축소 가능여부를 설정합니다
    map.setZoomable(zoomable);
};

//////////////////////////////////////////////////////'구'이름 마커/////////////////////////////////////////////////////////////

// 마커 이미지의 이미지 주소입니다
var imageSrc = "../static/img/marker.png";
// 마커 이미지의 이미지 크기 입니다
var imageSize = new kakao.maps.Size(75, 75);
// 마커 이미지를 생성합니다
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);



/////////////////////////////////////////////////필터되는 마커/////////////////////////////////////////////////////////

var image2Src = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

for (var i = 0; i < centerGu.length; i++) {

    // 마커 이미지의 이미지 크기 입니다
    var image2Size = new kakao.maps.Size(55, 55);
    // 마커 이미지를 생성합니다
    var marker2Image = new kakao.maps.MarkerImage(image2Src, image2Size);

    // 마커를 생성합니다
    var hospital_markers = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: centerGu[i].latlng, // 마커를 표시할 위치
        title: centerGu[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: marker2Image // 마커 이미지
    });
    hospital_markers.setMap(null);
    hospital.push(hospital_markers);
}




//////////////////////////////////////////////////////백그라운드폴리곤////////////////////////////////////////////////////////
function bgpolygon(ground, hole) {
    polygon1 = new kakao.maps.Polygon({
        map: map,
        path: [ground, hole], // 좌표 배열의 배열로 하나의 다각형을 표시할 수 있습니다
        strokeWeight: 2,
        strokeColor: '#b26bb2',
        strokeOpacity: 0.8,
        fillColor: '#e6e6fa',
        fillOpacity: 1
    });
}

/////////////////////////////////////////////////폴리곤 생성 및 이벤트 등록//////////////////////////////////////////////////////////

function displayArea(area) {
    var marker;
    var customOverlay1;
    var markerObj = new Object();

    // 다각형을 생성합니다
    var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: area.path, //왜 areas가 아니라 area인지 질문!!!!!!!
        strokeWeight: 3.0,
        strokeColor: '#7986cb',
        strokeOpacity: 0.5,
        fillColor: '#fff',
        fillOpacity: 1
    });


    var markerIdCounter = 1;

    for (var i = 0; i < centerGu.length; i++) {
        if (area.name == centerGu[i].title) {
            // 마커 ID 생성
            var markerId = 'marker_' + markerIdCounter++;

            marker = new kakao.maps.Marker({
                map: map,
                position: centerGu[i].latlng,
                title: centerGu[i].title,
                image: markerImage
            });

            var content = '<h5>' + centerGu[i].title + '<br>' + listCnt + '</h5>'

            // 커스텀 오버레이를 생성합니다
            customOverlay1 = new kakao.maps.CustomOverlay({
                map: map,
                position: centerGu[i].latlng,
                content: content,
                yAnchor: 1
            });

            // 마커와 ID를 polygonData에 추가
            markerObj = { polygon: polygon, marker: marker, customOverlay: customOverlay1, markerId: markerId };
            polygonData.push(markerObj);

            break;
        }

    }
    console.log(markerId);


    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function (mouseEvent) {
        polygon.setOptions({ fillColor: '#c5cae9' });

        customOverlay.setContent('<div id="area" class="area">' + area.name + '</div>');

        customOverlay.setPosition(mouseEvent.latLng);
        customOverlay.setMap(map);
    });

    // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
    kakao.maps.event.addListener(polygon, 'mousemove', function (mouseEvent) {
        customOverlay.setPosition(mouseEvent.latLng);
    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, 'mouseout', function () {
        polygon.setOptions({ fillColor: '#fff' });
        customOverlay.setMap(null);
    });


    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시하고 폴리곤을 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, 'click', function (click) {
        // 현재 지도 레벨에서 2레벨 확대한 레벨
        let level = (map.getLevel() <= 5 ? map.getLevel() : map.getLevel() - 3) // 현재 레벨에서 3를 더한 값을 사용합니다.
        polygon1.setOptions({ path: [ground, polygon.Ug] });

        // 클릭된 폴리곤의 중심 좌표를 얻기
        var polygonBounds = new kakao.maps.LatLngBounds();

        let gu = document.getElementById('area').innerText;

        let tmp = focusGu.filter(i => i.name == gu)[0].lanLng;

        area.path.forEach(function (latlng) {
            polygonBounds.extend(latlng);
        });

        // 지도를 클릭된 폴리곤의 중앙 위치를 기준으로 확대
        map.setLevel(level, {
            anchor: map.setCenter(new kakao.maps.LatLng(tmp.y, tmp.x)),
            animate: {
                duration: 350 // 확대 애니메이션 시간
            }
        });
        // 클릭된 폴리곤을 지도에서 제거 (숨기고 싶으면 setMap(null) 대신에 hide()를 사용)
        // polygon.setMap(null);
        MarkerController(polygon, null);

        // 이전에 지워진 폴리곤이 존재 시 다시 생성
        if (backup_polygon != null && backup_polygon != polygon) {
            MarkerController(backup_polygon, map)
        };

        // 지워진 폴리곤을 백업함.(다른 폴리곤 클릭 시 재생성을 위해 저장)
        backup_polygon = polygon;

        // 인포윈도우도 지도에서 제거
        infowindow.setMap(null);

        FilterMarker(hospital, map);


    });




};

//폴리곤, 마커, 오버레이 컨트롤러
function MarkerController(polygon, map) {
    polygonData.forEach(e => {
        if (e.polygon == polygon) {
            e.polygon.setMap(map);
            e.marker.setMap(map);
            e.customOverlay.setMap(map);
        }
    });
}

function FilterMarker(hospital, leve) {

    hospital.forEach( e => {
        if(map.getLevel() == 5) {
            e.setMap(leve);
        } else {
            e.setMap(leve);
        }
    })
}


$('#prev').on('click', function () {
    let level = (map.getLevel() <= 5 ? map.getLevel() + 3 : map.getLevel()) // 현재 레벨에서 3를 더한 값을 사용합니다.
    // map.getLevel()+2

    let y = 37.5639;
    let x = 126.9738;

    MarkerController(backup_polygon, map);

    map.setLevel(level, {
        anchor: map.setCenter(new kakao.maps.LatLng(y, x)),
        animate: {
            duration: 350 // 확대 애니메이션 시간
        }
    });

    FilterMarker(hospital, null);

});
selectJson();
// areas: 폴리곤 데이터를 담을 배열
let areas = [];

// ground: 배경 폴리곤의 좌표 설정을 담을 변수
let ground;

// polygonBG: 검은색 배경 폴리곤 객체
let polygonBG;

// backup_polygon: 이전에 클릭한 폴리곤의 백업을 담을 변수
let backup_polygon;

// polygonData: 폴리곤 및 마커 데이터를 담을 배열
const polygonData = [];

// hospital: 병원 마커를 담을 배열
const hospital = [];

// addresscount: 주소별 사용자 수를 담을 객체
const addresscount = {};

// 주소와 사용자 수를 객체에 매핑
$.each(address, function (i, addressName) {
  addresscount[addressName] = usercount[i];
});

// 문서가 준비되면 실행
$(document).ready(function () {
  // 권한을 확인하는 AJAX 요청
  $.ajax({
    url: "api/getauthorit",
    data: {
      id: login_id,
    },
    type: "POST",
    success: function (data) {
      // 권한이 있으면 관리자 버튼을 표시
      if (data == "권한") {
        $("#adminBtn").show();
      } else $("#adminBtn").hide();
    },
  });
  // 관리자 버튼 클릭시 이벤트
  $("#adminBtn").click(function () {
    // 관리자 페이지로 이동
    $.ajax({
      url: "/user/deshboard",
      type: "GET",
      success: function () {
        location.href = this.url;
      },
    });
  });
});

// 폴리곤 데이터를 받아와서 지도에 표시하는 함수
function setAreas(areas, data) {
  // bgpolygon 사이드 좌표 설정
  ground = [
    new kakao.maps.LatLng(37.7772, 126.4401),
    new kakao.maps.LatLng(37.255, 126.4518),
    new kakao.maps.LatLng(37.289, 127.5161),
    new kakao.maps.LatLng(37.8002, 127.4228),
  ];
  // 배경 폴리곤 생성
  // 검은색 배경 폴리곤 설정
  $.each(data, function (index, val) {
    let obj = new Object();
    let coordinates = val.geometry.coordinates;
    let name = val.properties.SIG_KOR_NM;
    var path = [];
    $.each(coordinates[0], function (i, coordinates) {
      //console.log(coordicates)를 확인해보면 [0]번재에 배열이 주로 저장이 됨. 그래서 [0]번째 배열에서 꺼내줌.
      path.push(new kakao.maps.LatLng(coordinates[1], coordinates[0])); // new kakao.maps.LatLng가 없으면 인식을 못해서 path 배열에 추가.
    });
    obj = { name: name, path: path };
    areas.push(obj);
  });
  polygonBG = new kakao.maps.Polygon({
    map: map,
    path: ground, // 좌표 배열의 배열로 하나의 다각형을 표시할 수 있습니다
    strokeWeight: 2,
    strokeColor: "#445A6EFF",
    strokeOpacity: 0.8,
    fillColor: "#e5e9ef",
    fillOpacity: 1,
  });
  displayArea(areas);
}

// JSON 데이터를 받아오고 지도에 표시하는 함수 호출
function selectJson() {
  $.getJSON("../JSON/geojson.json", function (geojson) {
    var data = geojson.features;
    setAreas(areas, data);
  });
}

//////////////////////////////////////////////////////////////지도 형성//////////////////////////////////////////////////

// 지도 초기 생성 라인
let mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    //지도에 들어가는 옵션 설정
    center: new kakao.maps.LatLng(37.5639, 126.9738), // 지도의 중심좌표
    level: 8, // 지도의 확대 레벨
    draggable: false, // 8level에서 지도 이동 불가
    disableDoubleClickZoom: true,
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
}

// 버튼 클릭에 따라 지도 이동 기능을 막거나 풀고 싶은 경우에는 map.setDraggable 함수를 사용합니다
function setDraggable(draggable) {
  // 마우스 드래그로 지도 이동 가능여부를 설정합니다
  map.setDraggable(draggable);
}

//////////////////////////////////////////////////////'구'이름 마커/////////////////////////////////////////////////////////////

// 마커 이미지의 이미지 주소입니다
var imageSrc = "../image/marker2.png";
// 마커 이미지의 이미지 크기 입니다
var imageSize = new kakao.maps.Size(45, 45);
// 마커 이미지를 생성합니다
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

/////////////////////////////////////////////////필터되는 마커/////////////////////////////////////////////////////////

// var image2Src = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
//
// for (var i = 0; i < mapData.length; i++) {
//
//     // 마커 이미지의 이미지 크기 입니다
//     var image2Size = new kakao.maps.Size(55, 55);
//     // 마커 이미지를 생성합니다
//     var marker2Image = new kakao.maps.MarkerImage(image2Src, image2Size);
//
//     // 마커를 생성합니다
//     var hospital_markers = new kakao.maps.Marker({
//         map: map, // 마커를 표시할 지도
//         position: mapData.latlng, // 마커를 표시할 위치
//         title: centerGu[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
//         image: marker2Image // 마커 이미지
//     });
//     hospital_markers.setMap(null);
//     hospital.push(hospital_markers);
// }

//////////////////////////////////////////////////////백그라운드폴리곤////////////////////////////////////////////////////////

/////////////////////////////////////////////////폴리곤 생성 및 이벤트 등록//////////////////////////////////////////////////////////

function displayArea(area) {
  let polygonArr = [];
  $.each(area, function (i, data) {
    // 다각형을 생성합니다
    let polygon = new kakao.maps.Polygon({
      map: map, // 다각형을 표시할 지도 객체
      path: data.path, //왜 areas가 아니라 area인지 질문!!!!!!!
      strokeWeight: 2.0,
      strokeColor: "#445A6EFF",
      strokeOpacity: 0.8,
      fillColor: "#FFF",
      fillOpacity: 1,
    });
    var polygonCenter;
    var addressId;
    $.each(mapData, function (i, value) {
      if (data.name == value.addressName) {
        polygonCenter = new kakao.maps.LatLng(
          parseFloat(value.mapY),
          parseFloat(value.mapX)
        );
        addressId = value.addressId;
        console.log(addressId);
      }
    });
    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, "mouseover", function (mouseEvent) {
      polygon.setOptions({ fillColor: "#FFFf8d" });
      customOverlay.setContent(
        '<div id="area" class="area">' + data.name + "</div>"
      );
      customOverlay.setPosition(mouseEvent.latLng);
      customOverlay.setMap(map);
    });

    // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
    kakao.maps.event.addListener(polygon, "mousemove", function (mouseEvent) {
      customOverlay.setPosition(mouseEvent.latLng);
    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, "mouseout", function () {
      polygon.setOptions({ fillColor: "#FFF" });
      customOverlay.setMap(null);
    });

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시하고 폴리곤을 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, "click", function (click) {
      const data = {
        loginId: login_id,
        addressId: addressId,
      };
      clickPolygon(data);
      setDraggable(true);
      polygon.setOptions({ fillColor: "#FFF" });
      // 현재 지도 레벨에서 2레벨 확대한 레벨
      let level = map.getLevel() <= 5 ? map.getLevel() : map.getLevel() - 3; // 현재 레벨에서 3를 더한 값을 사용합니다.

      // 지도를 클릭된 폴리곤의 중앙 위치를 기준으로 확대
      map.setLevel(level, {
        anchor: map.setCenter(polygonCenter),
        animate: {
          duration: 350, // 확대 애니메이션 시간
        },
      });
      // 클릭된 폴리곤을 지도에서 제거 (숨기고 싶으면 setMap(null) 대신에 hide()를 사용)
      polygonBG.setOptions({
        path: [ground, polygon.Ug],
      });
      MarkerController(polygon, null);

      // 이전에 지워진 폴리곤이 존재 시 다시 생성
      if (backup_polygon != null && backup_polygon != polygon) {
        MarkerController(backup_polygon, map);
      }
      // 지워진 폴리곤을 백업함.(다른 폴리곤 클릭 시 재생성을 위해 저장)
      backup_polygon = polygon;

      // 인포윈도우도 지도에서 제거
      infowindow.setMap(null);
    });
    polygonArr.push({ Name: data.name, polygon: polygon });
  });

  // 주소에 따라 마커와 오버레이 생성
  $.each(mapData, function (i, value) {
    // 주소에 해당하는 위도, 경도 좌표 생성
    let position = new kakao.maps.LatLng(
      parseFloat(value.mapY),
      parseFloat(value.mapX)
    );

    // 마커에 표시될 내용 작성 (주소 이름과 해당 주소의 사용자 수)
    let content =
      '<h5 id="Gu">' +
      value.addressName +
      "<br>" +
      addresscount[value.addressName] +
      "</h5>";

    // 마커 생성
    let marker = new kakao.maps.Marker({
      map: map,
      position: position,
      title: value.addressName,
      image: markerImage,
    });

    // 커스텀 오버레이 생성
    let customOverlay = new kakao.maps.CustomOverlay({
      map: map,
      position: position,
      content: content,
      yAnchor: 1,
    });

    // 주소에 따른 폴리곤 데이터와 연결
    $.each(polygonArr, function (i, polygonArrData) {
      if (polygonArrData.Name == value.addressName) {
        markerobj = {
          name: value.addressName,
          postion: position,
          marker: marker,
          customOverlay: customOverlay,
          polygon: polygonArrData.polygon,
        };
      }
    });

    // 생성된 객체를 폴리곤 데이터 배열에 추가
    polygonData.push(markerobj);
  });
}

// 폴리곤 클릭 이벤트를 처리하는 함수
function ClickEvent(polygonData) {
    // 이 함수는 클릭된 폴리곤의 정보를 인자로 받습니다.
    // 클릭된 폴리곤에 대한 추가적인 동작을 구현할 수 있습니다.
    // 클릭된 폴리곤의 정보를 활용하여 필요한 작업을 수행하세요.
}

//폴리곤, 마커, 오버레이 컨트롤러
function MarkerController(polygon, map) {
    // polygonData 배열을 순회하며 클릭된 폴리곤에 해당하는 객체를 찾아 지도에 표시합니다.
  polygonData.forEach((e) => {
    if (e.polygon == polygon) {
      e.polygon.setMap(map);
      e.marker.setMap(map);
      e.customOverlay.setMap(map);
    }
  });
}

// 마커 필터링 함수
function FilterMarker(hospital, leve) {
    // hospital 배열을 순회하며 마커를 화면에 표시 또는 숨깁니다.
  hospital.forEach((e) => {
    if (map.getLevel() == 5) {
      e.setMap(leve);
    } else {
      e.setMap(leve);
    }
  });
}

// '이전' 버튼 클릭 이벤트 핸들러
$("#prev").on("click", function () {
    // 이전 버튼 클릭 시 지도 레벨 및 위치를 조정하고 폴리곤 및 마커를 화면에 표시합니다.
  let level = map.getLevel() <= 5 ? map.getLevel() + 3 : map.getLevel(); // 현재 레벨에서 3를 더한 값을 사용합니다.

  // map.getLevel()+2
  let y = 37.5639;
  let x = 126.9738;

  setDraggable(false);

  MarkerController(backup_polygon, map);

  map.setLevel(level, {
    anchor: map.setCenter(new kakao.maps.LatLng(y, x)),
    animate: {
      duration: 350, // 확대 애니메이션 시간
    },
  });

  FilterMarker(hospital, null);
});

// 폴리곤 클릭 시 데이터 조회 및 화면 갱신 함수
function clickPolygon(list) {
  // 클릭된 폴리곤의 주소 ID를 서버에 전달하여 관련 데이터를 조회하고 화면을 갱신합니다.
  $.ajax({
    url: "/api/user?id=" + login_id + "&addressId=" + list.addressId, //로그인한 유저의 아이디로 자신 빼고 나머지 지역코드가 같은 유저를 찾음
    type: "GET",
    success: function (data) {
      // console.log("userList data:", data);
      // console.log("sender id: ", login_id);
      if (data !== undefined) {
        showList(data); // 지역코드가 같은 유저들의 정보를 화면에 리스트로 보여줌
      }
    },
  });
}
// function showProfile(user) {
//
//     let id = user.id;
//     let username = user.userId;
//     const category = user.category;
//     const buddyImage = user.buddyImage;
//     const buddyName = user.buddyName;
//     const buddyAge = user.buddyAge;
//     const buddyDetail = user.buddyDetail;
//     let buddySex = user.buddySex;
//     if (buddySex == 1) {
//         buddySex = '수';
//     } else {
//         buddySex = '암';
//     }
//     const row =
//         `
//             <div class="modal-header">
//                 <h1 class="modal-title">${username} 님의 프로필</h1>
//                 <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
//             </div>
//             <div class="modal-body">
//                 <img src="/image/list_icon.png">
//                 <img src="/image/dog1.jpg" style="width: 40px; height: 40px;">
//                 <p>이름 : ${buddyName}</p>
//                 <p>견종 : ${category}</p>
//                 <p>나이 : ${buddyAge}</p>
//                 <p>성별 : ${buddySex}</p>
//                 <p>상세설명 : ${buddyDetail}</p>
//             </div>
//             <div class="modal-footer">
//                     <button type="button" class="btn btn-outline-dark modal-btn" data-mc-btn="${id}" role="button">매칭하기</button>
//             </div>
//         `;
//
//     $("#profileModalContent").html(row);
//     $("#profileModal").modal("show");
// }

// DOM이 완전히 로드된 후 실행될 스크립트
document.addEventListener('DOMContentLoaded', function() {
    // 숨겨진 input에서 선택된 숙소 유형을 가져옴
    var selectedType = document.getElementById("selectedAccommodationType").value;
    var options = document.getElementsByClassName("accommodationType");

    // 해당 숙소 유형에 해당하는 라디오 버튼을 선택
    for (var i = 0; i < options.length; i++) {
        if (options[i].value === selectedType) {
            options[i].checked = true;
            break;
        }
    }
});

// 인원 증가/감소 버튼에 대한 이벤트 리스너 추가
document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('decrement')) {
        var roomId = event.target.getAttribute('data-room-id');
        var type = event.target.getAttribute('data-type');
        var inputField;

        // 인원 감소 처리
        if (type === 'basic') {
            inputField = document.getElementById('basicPeople-' + roomId);
        } else if (type === 'max') {
            inputField = document.getElementById('maxPeople-' + roomId);
        }

        // 현재 값에서 1 감소
        var currentValue = parseInt(inputField.value);
        if (currentValue > 1) {
            inputField.value = currentValue - 1;
        }
    }

    if (event.target && event.target.classList.contains('increment')) {
        var roomId = event.target.getAttribute('data-room-id');
        var type = event.target.getAttribute('data-type');
        var inputField;

        // 인원 증가 처리
        if (type === 'basic') {
            inputField = document.getElementById('basicPeople-' + roomId);
        } else if (type === 'max') {
            inputField = document.getElementById('maxPeople-' + roomId);
        }

        // 현재 값에서 1 증가
        var currentValue = parseInt(inputField.value);
        inputField.value = currentValue + 1;
    }

    // 객실 삭제 버튼 클릭 시 해당 객실 삭제
    if (event.target && event.target.classList.contains('btn-delete')) {
        var roomId = event.target.getAttribute('data-room-id');
        var roomContainer = document.getElementById('room-' + roomId);
        roomContainer.remove(); // 객실 요소 제거
        delete roomFiles[roomId]; // 해당 객실의 이미지 파일 배열 삭제
    }
});

// 숙소 유형 변경 시 선택된 값을 hidden input에 반영
document.querySelectorAll('.tag input[type="radio"]').forEach(function(radio) {
    radio.addEventListener('change', function() {
        var typeInput = document.getElementById('type');
        typeInput.value = this.getAttribute('data-tag');
    });
});

// 폼 유효성 검사 및 제출 방지
document.querySelector('form').addEventListener('submit', function(event) {
    if (!this.checkValidity()) {
        event.preventDefault(); // 폼 제출 중지
        alert('모든 필드를 채워주세요!');
    }
});

// 폼 데이터 생성 및 AJAX 요청 전송
document.getElementById('accommodationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    // FormData 객체 생성
    const formData = new FormData(this);

    // 부대시설 체크박스 선택된 값들을 FormData에 추가
    const selectedTags = [];
    document.querySelectorAll('.tags input[type="checkbox"]:checked').forEach(tag => {
        selectedTags.push(tag.getAttribute('data-tag'));
    });
    formData.append('facilities', selectedTags.join(', '));

    // 에디터의 내용을 FormData에 추가
    const editorContent = document.getElementById('editor').innerHTML;
    formData.append('accommodationInfo', editorContent);

    // 숙소 유형 추가
    const selectedType = document.querySelector('input[class="accommodationType"]:checked');
    if (selectedType) {
        formData.append('accommodationType', selectedType.value);
    }

    // 객실 정보 추가
    document.querySelectorAll('[id^="room-"]').forEach(function(roomElement, index) {
        const roomId = index + 1;
        formData.append(`rooms[${roomId}].roomName`, roomElement.querySelector('input[name="roomName"]').value);
        formData.append(`rooms[${roomId}].weekdayRate`, roomElement.querySelector('input[name="weekdayRate"]').value);
        formData.append(`rooms[${roomId}].fridayRate`, roomElement.querySelector('input[name="fridayRate"]').value);
        formData.append(`rooms[${roomId}].saturdayRate`, roomElement.querySelector('input[name="saturdayRate"]').value);
        formData.append(`rooms[${roomId}].sundayRate`, roomElement.querySelector('input[name="sundayRate"]').value);
        formData.append(`rooms[${roomId}].standardOccupation`, roomElement.querySelector('#basicPeople-' + roomId).value);
        formData.append(`rooms[${roomId}].maxOccupation`, roomElement.querySelector('#maxPeople-' + roomId).value);
        formData.append(`rooms[${roomId}].checkInTime`, roomElement.querySelector('#checkInTime-' + roomId).value);
        formData.append(`rooms[${roomId}].checkOutTime`, roomElement.querySelector('#checkOutTime-' + roomId).value);
    });

    // AJAX 요청 생성
    fetch('/accommodation-update', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            alert('업소 등록이 완료되었습니다!');
            window.location.href = '/enroll';
        } else {
            alert('업소 등록 중 오류가 발생했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('업소 등록 중 오류가 발생했습니다.');
    });
});

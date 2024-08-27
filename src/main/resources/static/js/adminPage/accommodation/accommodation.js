document.addEventListener('DOMContentLoaded', function () {

    // 이미지 미리보기 기능 추가
    const photoInput = document.getElementById('photo');
    const photoPreviewContainer = document.getElementById('photo-preview-container');
    const photoPreview = document.getElementById('photo-preview');

    // 대표 이미지 선택 시 미리보기 업데이트
    photoInput.addEventListener('change', function () {
        const file = this.files[0];

        if (file) {
            const reader = new FileReader();

            reader.addEventListener('load', function () {
                photoPreview.setAttribute('src', this.result);
                photoPreview.style.display = 'block';
            });

            reader.readAsDataURL(file);
        } else {
            photoPreview.setAttribute('src', '');
            photoPreview.style.display = 'none';
        }
    });

    let roomCount = 0;  // 방의 개수를 세기 위한 변수
    let roomFiles = {}; // 방과 관련된 파일을 저장하기 위한 객체
    let endIndex = [];  // 각 방의 마지막 인덱스를 저장하기 위한 배열

    // 숙소 유형 설정: 페이지가 로드될 때, 기존에 선택된 숙소 유형이 있다면 해당 유형을 체크
    const selectedType = document.getElementById("selectedAccommodationType");
    if (selectedType) {
        const options = document.getElementsByClassName("accommodationType");
        for (let i = 0; i < options.length; i++) {
            if (options[i].value === selectedType.value) {
                options[i].checked = true;
                break;
            }
        }
    }

    // 부대시설 태그 생성
    const tags = [
        { id: "wifi", label: "Wi-Fi" },
        { id: "parking", label: "주차장" },
        { id: "pool", label: "수영장" },
        { id: "gym", label: "헬스장" },
        { id: "restaurant", label: "레스토랑" },
        { id: "bar", label: "바" },
        { id: "laundry", label: "세탁실" },
        { id: "spa", label: "스파" },
        { id: "room_service", label: "룸 서비스" },
        { id: "concierge", label: "카페" },
        { id: "pet_friendly", label: "반려동물 동반 가능" },
        { id: "BBQ", label: "BBQ" },
        { id: "lounge", label: "라운지" }
    ];

    const tagContainer = document.getElementById('tagContainer');
    if (tagContainer) {
        tags.forEach(tag => {
            const tagDiv = document.createElement('div');
            tagDiv.classList.add('tag');

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.id = tag.id;
            checkbox.dataset.tag = tag.label;

            const label = document.createElement('label');
            label.setAttribute('for', tag.id);
            label.textContent = tag.label;

            tagDiv.appendChild(checkbox);
            tagDiv.appendChild(label);

            tagContainer.appendChild(tagDiv);
        });
    }

    // 선택된 태그를 부대시설 입력란에 표시하는 기능: 사용자가 선택한 태그들을 부대시설 입력란에 표시
    const subFacilityInput = document.getElementById('sub-facility');
    document.querySelectorAll('.tags input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const selectedTags = [];
            document.querySelectorAll('.tags input[type="checkbox"]:checked').forEach(tag => {
                selectedTags.push(tag.dataset.tag);
            });
            subFacilityInput.value = selectedTags.join(', ');
        });
    });

    // 방 추가 버튼 이벤트 리스너: '객실 추가' 버튼을 클릭하면 새로운 객실 입력 폼을 추가
    document.getElementById('addRoomBtn').addEventListener('click', function () {
        roomCount++;
        roomFiles[roomCount] = []; // 각 객실의 파일 배열 초기화
        const roomContainer = document.createElement('div');
        roomContainer.classList.add('room-container', 'mb-4', 'p-3', 'border', 'rounded');
        roomContainer.setAttribute('id', `room-${roomCount}`);

        // 객실 정보 입력 폼 생성
        roomContainer.innerHTML = `
            <div class="row mb-3">
                <label for="roomName-${roomCount}" class="col-sm-2 col-form-label">객실 이름</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="roomName" required>
                </div>
            </div>
            <table class="table table-bordered text-center">
                <thead>
                    <tr>
                        <th>요금 타입</th>
                        <th>주중</th>
                        <th>금요일</th>
                        <th>토요일</th>
                        <th>일요일</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>기본금액</td>
                        <td><input type="text" class="form-control" name="weekdayRate" required></td>
                        <td><input type="text" class="form-control" name="fridayRate" required></td>
                        <td><input type="text" class="form-control" name="saturdayRate" required></td>
                        <td><input type="text" class="form-control" name="sundayRate" required></td>
                    </tr>
                </tbody>
            </table>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">체크인 시간</label>
                <div class="col-sm-4">
                    <input type="time" class="form-control" name="checkInTime-${roomCount}" required>
                </div>
                <label class="col-sm-2 col-form-label">체크아웃 시간</label>
                <div class="col-sm-4">
                    <input type="time" class="form-control" name="checkOutTime-${roomCount}" required>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">기준인원</label>
                <div class="col-sm-10 d-flex align-items-center">
                    <div class="btn btn-secondary decrement" data-room-id="${roomCount}" data-type="basic">-</div>
                    <input type="text" class="form-control text-center mx-2" name="standardOccupation" id="basicPeople-${roomCount}" value="1" readonly>
                    <div class="btn btn-secondary increment" data-room-id="${roomCount}" data-type="basic">+</div>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">최대인원</label>
                <div class="col-sm-10 d-flex align-items-center">
                    <div class="btn btn-secondary decrement" data-room-id="${roomCount}" data-type="max">-</div>
                    <input type="text" class="form-control text-center mx-2" name="maxOccupation" id="maxPeople-${roomCount}" value="1" readonly>
                    <div class="btn btn-secondary increment" data-room-id="${roomCount}" data-type="max">+</div>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">객실 수</label>
                <div class="col-sm-10 d-flex align-items-center">
                    <div class="btn btn-secondary decrement" data-room-id="${roomCount}" data-type="count">-</div>
                    <input type="text" class="form-control text-center mx-2" name="roomCount" id="roomCount-${roomCount}" value="1" readonly>
                    <div class="btn btn-secondary increment" data-room-id="${roomCount}" data-type="count">+</div>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">이미지 등록</label>
                <div class="col-sm-10">
                    <input type="file" class="form-control" name="roomImages-${roomCount}[]" multiple>
                    <div id="preview-${roomCount}" class="mt-2 d-flex"></div>
                </div>
            </div>
            <button class="btn btn-danger btn-delete" data-room-id="${roomCount}">삭제</button>
        `;

        document.getElementById('roomsContainer').appendChild(roomContainer);

        // 다중 파일 선택 이벤트 리스너
        document.querySelector(`input[name="roomImages-${roomCount}[]"]`).addEventListener('change', function (event) {
            const files = event.target.files;
            const roomId = roomCount;
            const previewContainer = document.getElementById(`preview-${roomId}`);

            for (let i = 0; i < files.length; i++) {
                roomFiles[roomId].push(files[i]);
                const reader = new FileReader();
                reader.onload = function (e) {
                    const imgElement = document.createElement('img');
                    imgElement.src = e.target.result;
                    imgElement.style.maxHeight = '100px';
                    imgElement.style.maxWidth = '100px';
                    imgElement.style.marginRight = '10px';
                    previewContainer.appendChild(imgElement);
                };
                reader.readAsDataURL(files[i]);
            }

            // 배열의 현재 상태를 콘솔에 출력 (디버깅용)
            console.log(`Room ${roomId} files:`, roomFiles[roomId]);
        });
    });

    // 인원 증가/감소 버튼 이벤트 리스너 추가: 각 객실의 인원수 및 객실 수를 증가/감소시키는 버튼의 동작을 처리
    document.addEventListener('click', function (event) {
        if (event.target && event.target.classList.contains('decrement')) {
            const roomId = event.target.getAttribute('data-room-id');
            const type = event.target.getAttribute('data-type');
            let inputField;

            if (type === 'basic') {
                inputField = document.getElementById('basicPeople-' + roomId);
            } else if (type === 'max') {
                inputField = document.getElementById('maxPeople-' + roomId);
            } else if (type === 'count') {
                inputField = document.getElementById('roomCount-' + roomId);
            }

            let currentValue = parseInt(inputField.value);
            if (currentValue > 1) {
                inputField.value = currentValue - 1;
            }
        }

        if (event.target && event.target.classList.contains('increment')) {
            const roomId = event.target.getAttribute('data-room-id');
            const type = event.target.getAttribute('data-type');
            let inputField;

            if (type === 'basic') {
                inputField = document.getElementById('basicPeople-' + roomId);
            } else if (type === 'max') {
                inputField = document.getElementById('maxPeople-' + roomId);
            } else if (type === 'count') {
                inputField = document.getElementById('roomCount-' + roomId);
            }

            let currentValue = parseInt(inputField.value);
            inputField.value = currentValue + 1;
        }

        // 객실 삭제 버튼 클릭 시 해당 객실 삭제
        if (event.target && event.target.classList.contains('btn-delete')) {
            const roomId = event.target.getAttribute('data-room-id');
            const roomContainer = document.getElementById('room-' + roomId);
            roomContainer.remove();
            delete roomFiles[roomId]; // 해당 객실의 이미지 파일 배열 삭제
        }
    });

    // Form 데이터 생성 및 AJAX 요청 전송: 폼 제출 시 입력된 데이터를 서버로 전송
    document.getElementById('accommodationForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const formData = new FormData(this);

        // 객실 파일 추가
        for (let roomId in roomFiles) {
            for (let i = 0; i < roomFiles[roomId].length; i++) {
                formData.append(`roomImages-${roomId}[]`, roomFiles[roomId][i]);
            }
        }

        // 부대시설 체크박스 선택된 값들을 FormData에 추가
        const selectedTags = [];
        document.querySelectorAll('.tags input[type="checkbox"]:checked').forEach(tag => {
            selectedTags.push(tag.getAttribute('data-tag'));
        });
        formData.append('facilities', selectedTags.join(', '));

        // AJAX 요청 생성 및 전송
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
});

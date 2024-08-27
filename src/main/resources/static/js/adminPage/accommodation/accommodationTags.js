// DOM이 로드된 후 태그 아이콘 생성
document.addEventListener('DOMContentLoaded', function() {
    const tags = [
        { id: "wifi", label: "Wi-Fi" },
        { id: "parking", label: "주차장" },
        { id: "pool", label: "수영장" },
        { id: "gym", label: "헬스장" },
        { id: "restaurant", label: "레스토랑" },
        { id: "bar", label: "취사 가능" },
        { id: "laundry", label: "세탁실" },
        { id: "spa", label: "스파" },
        { id: "room_service", label: "룸 서비스" },
        { id: "concierge", label: "카페" },
        { id: "pet_friendly", label: "반려동물 동반 가능" },
        { id: "BBQ", label: "BBQ" },
        { id: "lounge", label: "라운지" }
    ];

    // tags 컨테이너를 찾아서 태그 아이콘을 추가합니다.
    const tagsContainer = document.querySelector('.tags');

    tags.forEach(tag => {
        const tagElement = document.createElement('div');
        tagElement.classList.add('tag');

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = tag.id;
        checkbox.setAttribute('data-tag', tag.label);

        const label = document.createElement('label');
        label.setAttribute('for', tag.id);
        label.textContent = tag.label;

        tagElement.appendChild(checkbox);
        tagElement.appendChild(label);
        tagsContainer.appendChild(tagElement);
    });
});

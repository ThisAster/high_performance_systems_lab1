let stompClient = null;

document.getElementById('connect').addEventListener('click', () => {
    console.log("Connecting to WebSocket...");

    const socket = new SockJS('/api/patients/ws/broker'); // Убедись, что этот путь совпадает с серверной конфигурацией
    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log("Connected: " + frame);
        document.getElementById('connect').disabled = true;
        document.getElementById('disconnect').disabled = false;

        // Подписка на сообщения
        stompClient.subscribe('/api/patients/ws/topic/created', (message) => {
            const receivedData = JSON.parse(message.body);
            const messagesList = document.getElementById('messages');
            const listItem = document.createElement('li');
            listItem.textContent = `Name: ${receivedData.name}, Date of Birth: ${receivedData.date_of_birth}, Email: ${receivedData.email}`;
            // Добавляем новое сообщение в начало списка
            messagesList.insertBefore(listItem, messagesList.firstChild);
        });
    }, (error) => {
        console.error("Error connecting to WebSocket:", error);
    });
});

document.getElementById('disconnect').addEventListener('click', () => {
    if (stompClient) {
        stompClient.disconnect(() => {
            console.log("Disconnected");
            document.getElementById('connect').disabled = false;
            document.getElementById('disconnect').disabled = true;
        });
    }
});

document.getElementById('send-form').addEventListener('submit', (event) => {
    event.preventDefault();

    // Извлечение данных из полей
    const name = document.getElementById('name').value.trim();
    const dateInput = document.getElementById('dateOfBirth');
    const email = document.getElementById('email').value.trim();

    // Преобразование даты в формат YYYY-MM-DD
    const dateOfBirth = dateInput.value ? dateInput.value : null;

    if (!name || !dateOfBirth || !email) {
        console.error("All fields are required!");
        alert("Please fill in all fields correctly.");
        return;
    }

    const dto = {
        name: name,
        date_of_birth: dateOfBirth, // Теперь дата строго в формате YYYY-MM-DD
        email: email
    };

    if (stompClient && stompClient.connected) {
        stompClient.send('/api/patients/ws/create', {}, JSON.stringify(dto));
        console.log('Sent:', dto);
    } else {
        console.error("WebSocket is not connected. Cannot send message.");
    }
});
let stompClient = null;

document.getElementById('connect').addEventListener('click', () => {
    console.log("Connecting to WebSocket...");

    const socket = new SockJS('/ws/broker'); // Убедись, что этот путь совпадает с серверной конфигурацией
    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log("Connected: " + frame);
        document.getElementById('connect').disabled = true;
        document.getElementById('disconnect').disabled = false;

        // Подписка на сообщения
        stompClient.subscribe('/ws/topic/deleted', (message) => {
            const receivedMessage = message.body; // Получаем сообщение как строку
            const messagesList = document.getElementById('messages');
            const listItem = document.createElement('li');
            listItem.textContent = `${receivedMessage}`;
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

    // Получаем строку из поля ввода
    const filename = document.getElementById('filename').value.trim();

    if (!filename) {
        console.error("Filename is required!");
        alert("Please enter a filename.");
        return;
    }

    if (stompClient && stompClient.connected) {
        // Отправляем строку на сервер
        stompClient.send('/ws/delete', {}, filename);
        console.log('Sent:', filename);
    } else {
        console.error("WebSocket is not connected. Cannot send message.");
    }
});
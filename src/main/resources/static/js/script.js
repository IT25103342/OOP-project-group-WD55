document.addEventListener('DOMContentLoaded', function() {
    setupFormValidation();
    setupAutoClose();
});

function setupFormValidation() {
    const bookingForm = document.getElementById('bookingForm');
    if (bookingForm) {
        bookingForm.addEventListener('submit', function(e) {
            const startTime = new Date(document.getElementById('startTime').value);
            const endTime = new Date(document.getElementById('endTime').value);

            if (endTime <= startTime) {
                e.preventDefault();
                alert('End time must be after start time!');
                return false;
            }
        });
    }

    const updateForm = document.getElementById('updateForm');
    if (updateForm) {
        updateForm.addEventListener('submit', function(e) {
            const startTime = new Date(document.getElementById('startTime').value);
            const endTime = new Date(document.getElementById('endTime').value);

            if (endTime <= startTime) {
                e.preventDefault();
                alert('End time must be after start time!');
                return false;
            }
        });
    }
}

function setupAutoClose() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });
}

function formatDateTime(dateString) {
    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    };
    return new Date(dateString).toLocaleDateString('en-US', options);
}

function confirmAction(message) {
    return confirm(message);
}

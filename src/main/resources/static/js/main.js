function selectAllCheckboxes() {
    const mainCheckbox = document.getElementById("mainCheck");
    let list = document.getElementsByName("checkRow");
    if (mainCheckbox.checked) {
        for (let i = 0; i < list.length; i++) {
            list[i].checked = true;
        }
    }
    if (!mainCheckbox.checked) {
        for (let i = 0; i < list.length; i++) {
            list[i].checked = false;
        }
    }
}

function aggregateAndSubmit(action, employee) {
    const list = document.getElementsByName("checkRow");
    let equipIds = '';
    for (let i = 0; i < list.length; i++) {
        if (list[i].checked) {
            equipIds += `${list[i].value} `;
        }
    }
    const form = document.getElementById("form");
    const equipField = document.createElement("input");
    const emplField = document.createElement("input");
    const empl = document.getElementById('employeeId').value;
    equipField.type = "hidden";
    equipField.name = "equipString";
    equipField.value = equipIds;
    form.appendChild(equipField);
    emplField.hidden = true;
    emplField.type = "number";
    emplField.name = "employeeId";
    emplField.value = employee ? empl : 0;
    form.appendChild(emplField);
    form.action = action;
    form.submit();
}

function setSaved(id) {
    const btn = document.getElementById(id);
    btn.classList.remove("btn-primary");
    btn.classList.add("btn-success", "disabled");
    btn.textContent = "Сохранено";
}
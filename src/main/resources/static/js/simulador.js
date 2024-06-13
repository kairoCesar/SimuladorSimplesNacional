    document.addEventListener("DOMContentLoaded", function() {
        // Adiciona eventos de clique aos botões de rádio
        const mercadoInternoRadio = document.getElementById('mercadoInterno');
        const mercadoExternoRadio = document.getElementById('mercadoExterno');
        mercadoInternoRadio.addEventListener('click', updateFormFields);
        mercadoExternoRadio.addEventListener('click', updateFormFields);
    });

      // Define o tema padrão como escuro
      document.body.classList.add('dark-mode');

    function updateFormFields() {
   const mercadoInternoChecked = document.getElementById('mercadoInterno').checked;

   const annexOption = document.getElementById('annexOption').value;
   const rbt12Field = document.getElementById('rbt12Field');
   const salesValueField = document.getElementById('salesValueField');
   const salesValueToExteriorField = document.getElementById('salesValueToExteriorField');
   const valueIcmsReplacementField = document.getElementById('valueIcmsReplacementField');
   const valuePisCofinsReplacementField = document.getElementById('valuePisCofinsReplacementField');

   // Oculta todos os campos
   rbt12Field.classList.add('hidden');
   salesValueField.classList.add('hidden');
   salesValueToExteriorField.classList.add('hidden');
   valueIcmsReplacementField.classList.add('hidden');
   valuePisCofinsReplacementField.classList.add('hidden');
   valueIssReplacementField.classList.add('hidden');

   //Para os campos obrigatorios
   salesValue.classList.add('hidden');
   salesValueToExterior.classList.add('hidden');

   // Mostra apenas os campos relevantes para o tipo de anexo selecionado
   if ((annexOption === '1' || annexOption === '2') && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      salesValue.classList.remove('hidden');
      valueIcmsReplacementField.classList.remove('hidden');
      valuePisCofinsReplacementField.classList.remove('hidden');
   } else if ((annexOption === '1' || annexOption === '2') && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExterior.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
   } else if ((annexOption === '3' || annexOption === '4' || annexOption === '5') && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      salesValue.classList.remove('hidden');
      valueIssReplacementField.classList.remove('hidden');
   } else if ((annexOption === '3' || annexOption === '4' || annexOption === '5') && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
      salesValueToExterior.classList.remove('hidden');
   } else if(annexOption === '6' && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      salesValue.classList.remove('hidden');
      valueIcmsReplacementField.classList.remove('hidden');
   } else if (annexOption === '6' && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
      salesValueToExterior.classList.remove('hidden');
   } else if (annexOption === '7' && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      salesValue.classList.remove('hidden');
   }  else if (annexOption === '7' && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
      salesValueToExterior.classList.remove('hidden');
   }

       // Define o valor como null se o campo estiver oculto
       if (salesValue.classList.contains('hidden')) {
           salesValue.value = null;
       }

           // Define o valor como null se o campo estiver oculto
           if (salesValueToExterior.classList.contains('hidden')) {
               salesValueToExterior.value = null;
           }

}

 document.addEventListener("DOMContentLoaded", function() {
    const rbt12Field = document.getElementById("rbt12");
    const form = rbt12Field.closest("form");

    form.addEventListener("submit", function(event) {
        if(validateFormFieldsRequired()){
            event.preventDefault(); // Impede o envio do formulário
        }

        //const rbt12Value = parseFloat(rbt12Field.value.replace(/\./g, '').replace(',', '.'));
        const rbt12Value = unmaskMoney(rbt12Field.value);
        console.log(rbt12Field.value);
        console.log(rbt12Value);
        if (rbt12Value > 3600000.00) {
            event.preventDefault(); // Impede o envio do formulário

            const modal = new bootstrap.Modal(document.getElementById('valueExceedModal'));
            modal.show();
        }
    });
});





function validateFormFieldsRequired() {
    const requiredFields = document.querySelectorAll('.obrigatorio:not(.hidden)');
    console.log(requiredFields);
    for (let field of requiredFields) {
        if (!field.value.trim()) {
            openErrorModal(field.placeholder);
            return true;
        }
    }
    return false;
}

/*
function openErrorModal(field) {
    const errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    errorModal.show();
}
*/

function openErrorModal(field) {
  const errorModal = new bootstrap.Modal(document.getElementById('errorModal'));

  const errorMessage = document.createElement('p');
  errorMessage.textContent = `"${field}"`; // Use template literal for clarity

  const modalBody = errorModal._element.querySelector('.modal-body');
  modalBody.textContent = ''; // Remove any previous content

  modalBody.appendChild(errorMessage);

  errorModal.show();
}

function unmaskMoney(value) {
    const valorSemMascara = value.replace(/[^\d\-+\.]/g, '');

      // Converte o valor sem máscara para double e retorna
      return parseFloat(valorSemMascara);
}
    document.addEventListener("DOMContentLoaded", function() {
        // Adiciona eventos de clique aos botões de rádio
        const mercadoInternoRadio = document.getElementById('mercadoInterno');
        const mercadoExternoRadio = document.getElementById('mercadoExterno');
        mercadoInternoRadio.addEventListener('click', updateFormFields);
        mercadoExternoRadio.addEventListener('click', updateFormFields);

        updateFormFields();
    });


      // Define o tema padrão como escuro
      document.body.classList.add('dark-mode');

function updateFormFields(loadPage) {
   const mercadoInternoChecked = document.getElementById('mercadoInterno').checked;

   const annexOption = document.getElementById('annexOption').value;
   const rbt12Field = document.getElementById('rbt12Field');
   const salesValueField = document.getElementById('salesValueField');
   const salesValueToExteriorField = document.getElementById('salesValueToExteriorField');
   const valueIcmsReplacementField = document.getElementById('valueIcmsReplacementField');
   const valuePisCofinsReplacementField = document.getElementById('valuePisCofinsReplacementField');
   const valueIssReplacementField = document.getElementById('valueIssReplacementField');

   const rbt12 = document.getElementById('rbt12');
   const salesValue = document.getElementById('salesValue');
   const salesValueToExterior = document.getElementById('salesValueToExterior');
   const valueIcmsReplacement = document.getElementById('valueIcmsReplacement');
   const valuePisCofinsReplacement = document.getElementById('valuePisCofinsReplacement');
   const valueIssReplacement = document.getElementById('valueIssReplacement');

   // Oculta todos os campos
   rbt12Field.classList.add('hidden');
   salesValueField.classList.add('hidden');
   salesValueToExteriorField.classList.add('hidden');
   valueIcmsReplacementField.classList.add('hidden');
   valuePisCofinsReplacementField.classList.add('hidden');
   valueIssReplacementField.classList.add('hidden');

    if(!showTable) {
       // Define o valor como null para todos os campos
       rbt12.value = null;
       salesValue.value = null;
       salesValueToExterior.value = null;
       valueIcmsReplacement.value = null;
       valuePisCofinsReplacement.value = null;
       valueIssReplacement.value = null;
    }

    showTable=false;

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
      salesValueToExteriorField.classList.remove('hidden');
      salesValueToExterior.classList.remove('hidden');
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
}


 document.addEventListener("DOMContentLoaded", function() {
    const rbt12Field = document.getElementById("rbt12");
    const form = rbt12Field.closest("form");

    form.addEventListener("submit", function(event) {

        if(validateFormFieldsRequired() || validateFieldsValueLessEquals()){
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

function validateFieldsValueLessEquals() {
    const salesValueField = document.getElementById("salesValue");

    const valueIcmsReplacementField = document.getElementById("valueIcmsReplacement");
    const valuePisCofinsReplacementField = document.getElementById("valuePisCofinsReplacement");
    const valueIssReplacementField = document.getElementById("valueIssReplacement");

    // Obtenha os valores dos campos e converta-os para números
    const salesValue = unmaskMoney(salesValueField.value) || 0;
    const valueIcmsReplacement = unmaskMoney(valueIcmsReplacementField.value) || 0;
    const valuePisCofinsReplacement = unmaskMoney(valuePisCofinsReplacementField.value) || 0;
    const valueIssReplacement = unmaskMoney(valueIssReplacementField.value) || 0;

    if(valueIcmsReplacement > salesValue) {
        openErrorModal("O valor de receita com ICMS retido por Substituição tributária não pode ser maior que o valor da Receita Bruta declarada");
        return true;
    }

    if(valuePisCofinsReplacement > salesValue) {
        openErrorModal("O valor de receita com tributação monofásica de Pis e Cofins não pode ser maior que o valor da Receita Bruta declarada");
        return true;
    }

    if(valueIssReplacement > salesValue) {
        openErrorModal("O valor de receita com retenção de ISS não pode ser maior que o valor da Receita Bruta declarada");
        return true
    }

    return false;
}


function validateFormFieldsRequired() {
    if(checkAnnexSelection()) {
        openErrorModal('Selecione um anexo válido');
        return true;
    }
    const requiredFields = document.querySelectorAll('.obrigatorio:not(.hidden)');
    for (let field of requiredFields) {
        if (!field.value.trim() || (unmaskMoney(field.value) == 0)) {
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
    const valorSemPontos = value.replace(/\./g, '');
    const valorComPonto = valorSemPontos.replace(',', '.');
    return parseFloat(valorComPonto);
}

function checkAnnexSelection() {
  const annexOption = document.getElementById('annexOption');
  const selectedValue = annexOption.value;
  console.log("selectedValue", selectedValue)

  if (selectedValue === '0') {
    return true;
  }

}

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

   // Mostra apenas os campos relevantes para o tipo de anexo selecionado
   if ((annexOption === '1' || annexOption === '2') && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      valueIcmsReplacementField.classList.remove('hidden');
      valuePisCofinsReplacementField.classList.remove('hidden');
   } else if ((annexOption === '1' || annexOption === '2') && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
   } else if ((annexOption === '3' || annexOption === '4' || annexOption === '5') && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      valueIssReplacementField.classList.remove('hidden');
   } else if ((annexOption === '3' || annexOption === '4' || annexOption === '5') && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
   } else if(annexOption === '6' && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
      valueIcmsReplacementField.classList.remove('hidden');
   } else if (annexOption === '6' && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
   } else if (annexOption === '7' && mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueField.classList.remove('hidden');
   }  else if (annexOption === '7' && !mercadoInternoChecked) {
      rbt12Field.classList.remove('hidden');
      salesValueToExteriorField.classList.remove('hidden');
   }
}

 document.addEventListener("DOMContentLoaded", function() {
    const rbt12Field = document.getElementById("rbt12");
    const form = rbt12Field.closest("form");

    form.addEventListener("submit", function(event) {
        //const rbt12Value = parseFloat(rbt12Field.value.replace(/\./g, '').replace(',', '.'));
        const rbt12Value = parseFloat(rbt12Field.value);
        console.log(rbt12Value);
        if (rbt12Value > 3600000.00) {
            event.preventDefault(); // Impede o envio do formulário

            const modal = new bootstrap.Modal(document.getElementById('valueExceedModal'));
            modal.show();
        }
    });
});
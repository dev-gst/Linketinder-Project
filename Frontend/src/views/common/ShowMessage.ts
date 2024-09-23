export class ShowMessage {
    
    public static showErrorMessage(message: string, locationID: string): void {
        const HTMLText: string = `
            <div class="error-message" id="${locationID + 'message'}">
                <p>${message}</p>
            </div>
        `;
        
        const form = document.getElementById(locationID);
        const errorID = document.getElementById(locationID + 'message');
        
        const error = document.createElement('div');
        error.innerHTML = HTMLText;

        if (!errorID) {
            form?.appendChild(error)
        } else {
            errorID.innerHTML = `<p>${message}</p>`;
        }
    }

    public static showSuccessMessage(message: string, locationID: string): void {
        const HTMLText: string = `
            <div class="success-message" id="${locationID + 'message'}">
                <p>${message}</p>
            </div>
        `;
        
        const form = document.getElementById(locationID);
        const successID = document.getElementById(locationID + 'message');
        
        const successContainer = document.createElement('div');
        successContainer.innerHTML = HTMLText;

        if (!successID) {
            form?.appendChild(successContainer)
        } else {
            successID.attributes.removeNamedItem('class');
            successID.attributes.setNamedItem(document.createAttribute('class'));
            successID.attributes.getNamedItem('class')!.value = 'success-message';
            successID.innerHTML = `<p>${message}</p>`;
        }
    }
}
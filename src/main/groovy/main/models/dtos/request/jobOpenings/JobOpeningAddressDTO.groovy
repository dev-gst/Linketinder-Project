package main.models.dtos.request.jobOpenings

class JobOpeningAddressDTO {

    final int AddressId
    final boolean isRemote

    JobOpeningAddressDTO(int AddressId, boolean isRemote) {
        // No validation is needed for AddressId and isRemote
        this.AddressId = AddressId
        this.isRemote = isRemote
    }
}

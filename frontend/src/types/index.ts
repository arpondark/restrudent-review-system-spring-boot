export interface Photo {
  url: string;
  uploadDate: string;
}

export interface Address {
  streetNumber: string;
  streetName: string;
  unit?: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}

export interface TimeRange {
  opentTime: string;
  closeTime: string;
}

export interface OperatingHour {
  monday?: TimeRange;
  tuesday?: TimeRange;
  wednesday?: TimeRange;
  thursday?: TimeRange;
  friday?: TimeRange;
  saturday?: TimeRange;
  sunday?: TimeRange;
}

export interface User {
  id: string;
  username: string;
  givename: string;
  familyName: string;
}

export interface Restaurant {
  id: string;
  name: string;
  cuisineType: string;
  contactInformation: string;
  averageRating?: number;
  latitude: number;
  longitude: number;
  address: Address;
  operatingHours?: OperatingHour;
  photos?: Photo[];
  createdBy?: User;
}

export interface CreateRestaurantRequest {
  name: string;
  cuisineType: string;
  contactInformation: string;
  latitude: number;
  longitude: number;
  address: Address;
  operatingHours?: OperatingHour;
  photos?: Photo[];
}

export interface Page<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  totalElements: number;
  totalPages: number;
  last: boolean;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

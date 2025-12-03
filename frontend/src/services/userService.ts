import { api } from './api';

export const userService = {
  getDashboard: () => api.get('/user/dashboard'),
  makeReservation: (payload: any) => api.post('/reservations', payload),
};


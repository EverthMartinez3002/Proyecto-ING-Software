import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import axiosInstance from './axios';
import Swal from 'sweetalert2';

const exportExcel = async (startDate, endDate, entryType) => {
  try {
    const response = await axiosInstance.get('/api/entries/export', {
      params: {
        startDate,
        endDate,
        entryType,
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      responseType: 'blob',
    });    

    const csvData = await response.data.text();

    const rows = XLSX.utils.sheet_to_json(
      XLSX.read(csvData, { type: 'string' }).Sheets['Sheet1']
    );

    const formattedRows = rows.map(row => {
        if (row.Fecha) {
          const excelSerialDate = Number(row.Fecha);
          const jsDate = new Date((excelSerialDate - 25569) * 86400 * 1000); 
          row.Fecha = jsDate.toLocaleDateString('es-ES');
        }
        return row;
      });

    const worksheet = XLSX.utils.json_to_sheet(formattedRows);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Reporte');

    const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    const blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
    saveAs(blob, `reporte-${startDate}-${endDate}.xlsx`);
  } catch (error) {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Error al procesar el archivo Excel',
      });
    throw error;
  }
};

export default exportExcel;

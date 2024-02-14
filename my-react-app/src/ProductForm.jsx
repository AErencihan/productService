import React, { useState } from 'react';
import axios from 'axios';

function ProductForm() {
    const [name, setName] = useState('');
    const [status, setStatus] = useState('');
    const [file, setFile] = useState(null);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('name', name);
        formData.append('status', status);
        formData.append('file', file);

        const token = "buraya-bearer-token"; // Bearer token buraya eklenecek

        try {
            const response = await axios.post('http://localhost:8084/api/product/create', formData, {
                headers: {
                    'Authorization': 'Bearer ' + eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlcm5uIiwiaXNzIjoiZXJuIiwiZXhwIjoxNzA3NjY1MTQ5fQ.zXjKM5MxWgGeWQxok8m3xg8MDmIR20sU31jXSd4FEBQ,
                    'Content-Type': 'multipart/form-data'
                }
            });

            console.log('response:', response);
            console.log('Ürün başarıyla eklendi');
        } catch (error) {
            console.error('Ürün eklenirken hata oluştu:', error);
        }
    };

    return (
        <div>
            <h1>Ürün Ekle</h1>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <label htmlFor="name">Ürün Adı:</label>
                <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} /><br /><br />
                <label htmlFor="status">Durumu:</label>
                <input type="text" id="status" value={status} onChange={(e) => setStatus(e.target.value)} /><br /><br />
                <label htmlFor="file">Dosya Seç:</label>
                <input type="file" id="file" onChange={(e) => setFile(e.target.files[0])} /><br /><br />
                <button type="submit">Gönder</button>
            </form>
        </div>
    );
}

export default ProductForm;


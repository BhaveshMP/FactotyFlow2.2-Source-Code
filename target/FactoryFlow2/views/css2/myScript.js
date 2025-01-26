fetch('https://api.pexels.com/v1/search?query=monitor', {
  headers: {
    Authorization: 'Iaoofsk1OWfG109K2tstmnNiZwl8PUdqYCb0oIDJoyZZghblL2CpiCqM'
  }
})
.then(response => response.json())
.then(data => {
  document.getElementById('image123').src = data.photos[0].src.medium;
});

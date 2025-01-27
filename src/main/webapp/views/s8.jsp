<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Check</title>
    <script>
        // This function checks if the first product card is available
        function checkProductCard() {
            var element = document.evaluate("//div[@class='card card-product'][1]", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;

            if (element) {
                console.log("Element found:", element);
                if (element.offsetParent !== null) {
                    console.log("Element is visible and interactable.");
                } else {
                    console.log("Element is not visible.");
                }
            } else {
                console.log("Element not found.");
            }
        }

        // Run the function once the page has loaded
        window.onload = checkProductCard;
    </script>
</head>
<body>
</body>
</html>

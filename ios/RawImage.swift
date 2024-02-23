@objc(RawImage)
class RawImage: NSObject {
    
    @objc(rawToPng:withResolver:withRejecter:)
    func rawToPng(path: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        let imageURL = URL(fileURLWithPath: path)
        let imageData = try? Data(contentsOf: imageURL)
        if let imageData = imageData, let image = UIImage(data: imageData) {
            let outputPath = convertToPNG(image: image)
            if ((outputPath?.hashValue) != nil){
                resolve(outputPath)
            }
            else {
                reject("Error converting image", "IMAGE_CONVERTION_ERROR", nil)
            }
        } else {
            reject("Error loading image from path", "IMAGE_LOADING_ERROR", nil)
        }
    }
    
    func convertToPNG(image: UIImage) -> String? {
        var outputPath : String? = nil
        if let jpegImage = image.pngData(){
            
            do {
                //Convert
                let tempDirectoryURL = URL(fileURLWithPath:  NSTemporaryDirectory(), isDirectory: true)
                let newFileName = "\(UUID().uuidString).png"
                let targetURL = tempDirectoryURL.appendingPathComponent(newFileName)
                try jpegImage.write(to: targetURL, options: [])
                outputPath = targetURL.relativePath
            }catch {
                print (error.localizedDescription)
                print("FAILED")
            }
        }else{
            print("FAILED")
        }
        return outputPath
    }
}
